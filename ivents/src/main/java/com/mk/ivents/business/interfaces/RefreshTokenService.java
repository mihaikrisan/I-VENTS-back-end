package com.mk.ivents.business.interfaces;

import com.mk.ivents.business.exceptions.InvalidRefreshTokenException;
import com.mk.ivents.persistence.models.RefreshToken;

public interface RefreshTokenService {
    RefreshToken generateRefreshToken();

    void validateRefreshToken(String token) throws InvalidRefreshTokenException;

    void deleteRefreshToken(String token);
}
