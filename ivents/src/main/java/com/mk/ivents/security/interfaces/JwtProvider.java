package com.mk.ivents.security.interfaces;

import com.mk.ivents.security.exceptions.KeyRetrievalException;
import org.springframework.security.core.Authentication;

public interface JwtProvider {
    String generateToken(Authentication authentication) throws KeyRetrievalException;

    String generateTokenWithUserName(String username) throws KeyRetrievalException;

    boolean validateToken(String jwt) throws KeyRetrievalException;

    String getUsernameFromJwt(String token) throws KeyRetrievalException;

    Long getJwtExpirationTimeInMillis();
}
