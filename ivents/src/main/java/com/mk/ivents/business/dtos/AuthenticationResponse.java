package com.mk.ivents.business.dtos;

import com.mk.ivents.persistence.constants.UserRole;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;

@NoArgsConstructor
@Getter
@Setter
public class AuthenticationResponse {
    private String authenticationToken;
    private String refreshToken;
    private Instant expirationTime;
    private String username;
    private UserRole userRole;
}
