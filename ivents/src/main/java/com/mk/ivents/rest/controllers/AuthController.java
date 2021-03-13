package com.mk.ivents.rest.controllers;

import com.mk.ivents.business.dtos.AuthenticationResponse;
import com.mk.ivents.business.dtos.LoginRequest;
import com.mk.ivents.business.dtos.RefreshTokenRequest;
import com.mk.ivents.business.dtos.RegisterRequest;
import com.mk.ivents.business.exceptions.InvalidRefreshTokenException;
import com.mk.ivents.business.exceptions.InvalidVerificationTokenException;
import com.mk.ivents.business.exceptions.UsernameAlreadyTakenException;
import com.mk.ivents.business.interfaces.AuthService;
import com.mk.ivents.security.exceptions.KeyRetrievalException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/auth")
public class AuthController {
    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/signup")
    public ResponseEntity<String> signUp(@Valid @RequestBody RegisterRequest registerRequest) throws UsernameAlreadyTakenException {
        authService.signUp(registerRequest);

        return ResponseEntity.ok("User registration successful");
    }

    @GetMapping("/verify/{tokenUuid}")
    public ResponseEntity<String> verifyAccount(@PathVariable String tokenUuid) throws InvalidVerificationTokenException {
        authService.verifyAccount(tokenUuid);

        return ResponseEntity.ok("Account activated successfully");
    }

    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> logIn(@Valid @RequestBody LoginRequest loginRequest) throws KeyRetrievalException {
        AuthenticationResponse authenticationResponse = authService.logIn(loginRequest);

        return ResponseEntity.ok(authenticationResponse);
    }

    @PostMapping("refresh/token")
    public ResponseEntity<AuthenticationResponse> refreshTokens(@Valid @RequestBody RefreshTokenRequest refreshTokenRequest) throws KeyRetrievalException, InvalidRefreshTokenException {
        return ResponseEntity.ok(authService.refreshToken(refreshTokenRequest));
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logOut(@Valid @RequestBody RefreshTokenRequest refreshTokenRequest) throws InvalidRefreshTokenException {
        authService.logOut(refreshTokenRequest);

        return ResponseEntity.ok("Logged out successfully");
    }
}
