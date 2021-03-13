package com.mk.ivents.business.interfaces;

import com.mk.ivents.business.dtos.AuthenticationResponse;
import com.mk.ivents.business.dtos.LoginRequest;
import com.mk.ivents.business.dtos.RefreshTokenRequest;
import com.mk.ivents.business.dtos.RegisterRequest;
import com.mk.ivents.business.exceptions.InvalidRefreshTokenException;
import com.mk.ivents.business.exceptions.InvalidVerificationTokenException;
import com.mk.ivents.business.exceptions.NotFoundException;
import com.mk.ivents.business.exceptions.UsernameAlreadyTakenException;
import com.mk.ivents.persistence.models.User;
import com.mk.ivents.persistence.models.VerificationToken;
import com.mk.ivents.security.exceptions.KeyRetrievalException;

public interface AuthService {
    void signUp(RegisterRequest registerRequest) throws UsernameAlreadyTakenException;

    String generateVerificationToken(User user);

    void verifyAccount(String tokenUuid) throws InvalidVerificationTokenException;

    void fetchUserAndEnable(VerificationToken verificationToken) throws NotFoundException;

    AuthenticationResponse logIn(LoginRequest loginRequest) throws KeyRetrievalException;

    AuthenticationResponse refreshToken(RefreshTokenRequest refreshTokenRequest) throws InvalidRefreshTokenException, KeyRetrievalException;

    void logOut(RefreshTokenRequest refreshTokenRequest) throws InvalidRefreshTokenException;
}
