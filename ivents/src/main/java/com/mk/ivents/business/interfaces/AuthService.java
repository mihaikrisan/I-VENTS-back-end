package com.mk.ivents.business.interfaces;

import com.mk.ivents.business.dtos.AuthenticationResponse;
import com.mk.ivents.business.dtos.LoginRequest;
import com.mk.ivents.business.dtos.RefreshTokenRequest;
import com.mk.ivents.business.dtos.RegisterRequest;
import com.mk.ivents.business.exceptions.*;
import com.mk.ivents.persistence.models.User;
import com.mk.ivents.persistence.models.VerificationToken;
import com.mk.ivents.security.exceptions.KeyRetrievalException;

public interface AuthService {
    void signUp(RegisterRequest registerRequest) throws SignupException;

    String generateVerificationToken(User user);

    void verifyAccount(String tokenUuid) throws InvalidVerificationTokenException;

    void fetchUserAndEnable(VerificationToken verificationToken) throws NotFoundException;

    AuthenticationResponse logIn(LoginRequest loginRequest) throws KeyRetrievalException;

    AuthenticationResponse refreshToken(RefreshTokenRequest refreshTokenRequest) throws InvalidRefreshTokenException, KeyRetrievalException;

    void forgotPassword(String email);

    void resetPassword(String resetCode, String newPassword) throws InvalidVerificationTokenException, OldPasswordException;

    void logOut(RefreshTokenRequest refreshTokenRequest) throws InvalidRefreshTokenException;
}
