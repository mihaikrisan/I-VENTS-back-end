package com.mk.ivents.business.services;

import com.mk.ivents.business.dtos.AuthenticationResponse;
import com.mk.ivents.business.dtos.LoginRequest;
import com.mk.ivents.business.dtos.RefreshTokenRequest;
import com.mk.ivents.business.dtos.RegisterRequest;
import com.mk.ivents.business.exceptions.*;
import com.mk.ivents.business.interfaces.*;
import com.mk.ivents.business.models.ResetPasswordEmail;
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
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

@Service
@Transactional
public class AuthServiceImpl implements AuthService {
    private final PasswordEncoder passwordEncoder;
    private final UserService userService;
    private final UserProfileService userProfileService;
    private final VerificationTokenRepository verificationTokenRepository;
    private final MailService mailService;
    private final AuthenticationManager authenticationManager;
    private final JwtProvider jwtProvider;
    private final RefreshTokenService refreshTokenService;

    public AuthServiceImpl(PasswordEncoder passwordEncoder, UserService userService, UserProfileService userProfileService,
                           VerificationTokenRepository verificationTokenRepository,
                           MailService mailService, AuthenticationManager authenticationManager,
                           JwtProvider jwtProvider, RefreshTokenService refreshTokenService) {
        this.passwordEncoder = passwordEncoder;
        this.userService = userService;
        this.userProfileService = userProfileService;
        this.verificationTokenRepository = verificationTokenRepository;
        this.mailService = mailService;
        this.authenticationManager = authenticationManager;
        this.jwtProvider = jwtProvider;
        this.refreshTokenService = refreshTokenService;
    }

    @Override
    public void signUp(RegisterRequest registerRequest) throws SignupException {
        String username = registerRequest.getUsername();
        String email = registerRequest.getEmail();
        String phoneNumber = registerRequest.getPhoneNumber();
        boolean isError = false;
        String errorMessage = "";

        if (userService.isUsernameAlreadyTaken(username)) {
            isError = true;
            errorMessage += "This username is already taken.\n";
        }

        if (userProfileService.isEmailAlreadyTaken(email)) {
            isError = true;
            errorMessage += "This email is already taken.\n";
        }

        if (userProfileService.isPhoneNumberAlreadyTaken(phoneNumber)) {
            isError = true;
            errorMessage += "This phone number is already taken.\n";
        }

        if (isError) {
            throw new SignupException(errorMessage.trim());
        }

        UserProfile userProfile = new UserProfile();
        userProfile.setFirstName(registerRequest.getFirstName());
        userProfile.setLastName(registerRequest.getLastName());
        userProfile.setAge(registerRequest.getAge());
        userProfile.setEmail(email);
        userProfile.setPhoneNumber(phoneNumber);

        User user = new User();
        user.setUsername(username);
        user.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
        user.setUserRole(UserRole.valueOf(registerRequest.getUserRole()));
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

        fetchUserAndEnable(verificationToken);
        verificationTokenRepository.delete(verificationToken);
    }

    @Override
    public void fetchUserAndEnable(VerificationToken verificationToken) {
        User user = verificationToken.getUser();
        user.setEnabled(true);
        userService.save(user);
    }

    @Override
    public AuthenticationResponse logIn(LoginRequest loginRequest) throws KeyRetrievalException, NotFoundException {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername(),
                loginRequest.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);

        String token = jwtProvider.generateToken(authentication);

        AuthenticationResponse authenticationResponse = new AuthenticationResponse();
        authenticationResponse.setAuthenticationToken(token);
        authenticationResponse.setRefreshToken(refreshTokenService.generateRefreshToken().getToken());
        authenticationResponse.setExpirationTime(Instant.now().plusMillis(jwtProvider.getJwtExpirationTimeInMillis()));
        authenticationResponse.setUserId(userService.findByUsername(loginRequest.getUsername()).getId());
        authenticationResponse.setUsername(loginRequest.getUsername());
        authenticationResponse.setUserRole(UserRole.valueOf(authentication.getAuthorities()
                .toArray(new GrantedAuthority[0])[0].getAuthority()));

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

        User user;
        try {
            user = userService.findByUsername(refreshTokenRequest.getUsername());
        } catch (NotFoundException notFoundException) {
            throw new InvalidRefreshTokenException("Token '" + refreshTokenRequest.getRefreshToken()
                    + "' does not correspond to a registered user");
        }

        authenticationResponse.setUserRole(user.getUserRole());

        return authenticationResponse;
    }

    @Override
    public void forgotPassword(String email) {
        User user;
        try {
            user = userService.findByEmail(email);
        } catch (NotFoundException notFoundException) {
            return;
        }

        String resetCode = generateVerificationToken(user);

        ResetPasswordEmail resetPasswordEmail = new ResetPasswordEmail();
        resetPasswordEmail.setSubject("I-vents account password reset");
        resetPasswordEmail.setRecipient(email);
        resetPasswordEmail.setResetCode(resetCode);

        mailService.sendResetPasswordEmail(resetPasswordEmail);
    }

    @Override
    public void resetPassword(String resetCode, String newPassword) throws InvalidVerificationTokenException, OldPasswordException {
        Optional<VerificationToken> verificationTokenOptional = verificationTokenRepository.findByUuid(resetCode);
        VerificationToken verificationToken = verificationTokenOptional.orElseThrow(() ->
                new InvalidVerificationTokenException("Did not find reset code '" + resetCode + "'"));

        if (Instant.now().isAfter(verificationToken.getExpiryDate())) {
            throw new InvalidVerificationTokenException("Reset code is expired");
        }

        User user = verificationToken.getUser();

        if (passwordEncoder.matches(newPassword, user.getPassword())) {
            throw new OldPasswordException("New password must be different from old password");
        }

        String newPasswordHash = passwordEncoder.encode(newPassword);
        user.setPassword(newPasswordHash);
        userService.save(user);

        verificationTokenRepository.delete(verificationToken);
    }

    @Override
    public void logOut(RefreshTokenRequest refreshTokenRequest) throws InvalidRefreshTokenException {
        String refreshToken = refreshTokenRequest.getRefreshToken();

        refreshTokenService.validateRefreshToken(refreshToken);
        refreshTokenService.deleteRefreshToken(refreshToken);
    }
}
