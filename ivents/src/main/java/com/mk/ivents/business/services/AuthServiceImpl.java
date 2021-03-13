package com.mk.ivents.business.services;

import com.mk.ivents.business.dtos.AuthenticationResponse;
import com.mk.ivents.business.dtos.LoginRequest;
import com.mk.ivents.business.dtos.RefreshTokenRequest;
import com.mk.ivents.business.dtos.RegisterRequest;
import com.mk.ivents.business.exceptions.InvalidRefreshTokenException;
import com.mk.ivents.business.exceptions.InvalidVerificationTokenException;
import com.mk.ivents.business.exceptions.NotFoundException;
import com.mk.ivents.business.exceptions.UsernameAlreadyTakenException;
import com.mk.ivents.business.interfaces.AuthService;
import com.mk.ivents.business.interfaces.MailService;
import com.mk.ivents.business.interfaces.RefreshTokenService;
import com.mk.ivents.business.interfaces.UserService;
import com.mk.ivents.business.models.VerificationEmail;
import com.mk.ivents.persistence.constants.UserRole;
import com.mk.ivents.persistence.interfaces.VerificationTokenRepository;
import com.mk.ivents.persistence.models.User;
import com.mk.ivents.persistence.models.UserProfile;
import com.mk.ivents.persistence.models.VerificationToken;
import com.mk.ivents.security.exceptions.KeyRetrievalException;
import com.mk.ivents.security.interfaces.JwtProvider;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Optional;
import java.util.UUID;

@Service
@Transactional
public class AuthServiceImpl implements AuthService {
    private final PasswordEncoder passwordEncoder;
    private final UserService userService;
    private final VerificationTokenRepository verificationTokenRepository;
    private final MailService mailService;
    private final AuthenticationManager authenticationManager;
    private final JwtProvider jwtProvider;
    private final RefreshTokenService refreshTokenService;

    public AuthServiceImpl(PasswordEncoder passwordEncoder, UserService userService,
                           VerificationTokenRepository verificationTokenRepository,
                           MailService mailService, AuthenticationManager authenticationManager,
                           JwtProvider jwtProvider, RefreshTokenService refreshTokenService) {
        this.passwordEncoder = passwordEncoder;
        this.userService = userService;
        this.verificationTokenRepository = verificationTokenRepository;
        this.mailService = mailService;
        this.authenticationManager = authenticationManager;
        this.jwtProvider = jwtProvider;
        this.refreshTokenService = refreshTokenService;
    }

    @Override
    public void signUp(RegisterRequest registerRequest) throws UsernameAlreadyTakenException {
        String username = registerRequest.getUsername();

        try {
            userService.findByUsername(username);

            throw new UsernameAlreadyTakenException("The username '" + username + "' is already taken");
        } catch (NotFoundException notFoundException) {
            UserProfile userProfile = new UserProfile();
            userProfile.setFirstName(registerRequest.getFirstName());
            userProfile.setLastName(registerRequest.getLastName());
            userProfile.setAge(registerRequest.getAge());
            userProfile.setEmail(registerRequest.getEmail());
            userProfile.setPhoneNumber(registerRequest.getPhoneNumber());
            userProfile.setPreferredEventCategories(new ArrayList<>());

            User user = new User();
            user.setUsername(username);
            user.setPassword(passwordEncoder.encode(registerRequest.getPassword()));

            UserRole userRole = null;
            for (UserRole role : UserRole.values()) {
                if (role.toString().equals(registerRequest.getUserRole())) {
                    userRole = role;
                }
            }

            user.setUserRole(userRole);
            user.setEnabled(false);
            user.setUserProfile(userProfile);

            userService.save(user);

            String tokenUuid = generateVerificationToken(user);

            VerificationEmail verificationEmail = new VerificationEmail();
            verificationEmail.setSubject("I-vents account verification");
            verificationEmail.setRecipient(userProfile.getEmail());
            verificationEmail.setTokenUuid(tokenUuid);

            mailService.sendVerificationEmail(verificationEmail);
        }
    }

    @Override
    public String generateVerificationToken(User user) {
        String uuid = UUID.randomUUID().toString();

        VerificationToken verificationToken = new VerificationToken();
        verificationToken.setUuid(uuid);
        verificationToken.setUser(user);
        verificationToken.setExpiryDate(Instant.now().plus(Duration.ofDays(1)));

        verificationTokenRepository.save(verificationToken);

        return uuid;
    }

    @Override
    public void verifyAccount(String tokenUuid) throws InvalidVerificationTokenException {
        Optional<VerificationToken> verificationTokenOptional = verificationTokenRepository.findByUuid(tokenUuid);
        VerificationToken verificationToken = verificationTokenOptional.orElseThrow(() -> new InvalidVerificationTokenException("Did not find token '" + tokenUuid + "'"));

        if (Instant.now().isAfter(verificationToken.getExpiryDate())) {
            throw new InvalidVerificationTokenException("Token is expired");
        }

        try {
            fetchUserAndEnable(verificationToken);
        } catch (NotFoundException notFoundException) {
            throw new InvalidVerificationTokenException("Token '" + tokenUuid + "' does not correspond to a " +
                    "registered user");
        }
    }

    @Override
    public void fetchUserAndEnable(VerificationToken verificationToken) throws NotFoundException {
        int userId = verificationToken.getUser().getId();

        User user = userService.findById(userId);
        user.setEnabled(true);
        userService.save(user);
    }

    @Override
    public AuthenticationResponse logIn(LoginRequest loginRequest) throws KeyRetrievalException {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername(),
                loginRequest.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);

        String token = jwtProvider.generateToken(authentication);

        AuthenticationResponse authenticationResponse = new AuthenticationResponse();
        authenticationResponse.setAuthenticationToken(token);
        authenticationResponse.setRefreshToken(refreshTokenService.generateRefreshToken().getToken());
        authenticationResponse.setExpirationTime(Instant.now().plusMillis(jwtProvider.getJwtExpirationTimeInMillis()));
        authenticationResponse.setUsername(loginRequest.getUsername());

        return authenticationResponse;
    }

    @Override
    @Transactional(readOnly = true)
    public AuthenticationResponse refreshToken(RefreshTokenRequest refreshTokenRequest) throws InvalidRefreshTokenException, KeyRetrievalException {
        refreshTokenService.validateRefreshToken(refreshTokenRequest.getRefreshToken());
        String token = jwtProvider.generateTokenWithUserName(refreshTokenRequest.getUsername());

        AuthenticationResponse authenticationResponse = new AuthenticationResponse();
        authenticationResponse.setAuthenticationToken(token);
        authenticationResponse.setRefreshToken(refreshTokenRequest.getRefreshToken());
        authenticationResponse.setExpirationTime(Instant.now().plusMillis(jwtProvider.getJwtExpirationTimeInMillis()));
        authenticationResponse.setUsername(refreshTokenRequest.getUsername());

        return authenticationResponse;
    }

    @Override
    public void logOut(RefreshTokenRequest refreshTokenRequest) throws InvalidRefreshTokenException {
        String refreshToken = refreshTokenRequest.getRefreshToken();

        refreshTokenService.validateRefreshToken(refreshToken);
        refreshTokenService.deleteRefreshToken(refreshToken);
    }
}
