package com.mk.ivents.rest.controllers;

import com.mk.ivents.business.dtos.*;
import com.mk.ivents.business.exceptions.InvalidRefreshTokenException;
import com.mk.ivents.business.exceptions.InvalidVerificationTokenException;
import com.mk.ivents.business.exceptions.OldPasswordException;
import com.mk.ivents.business.exceptions.SignupException;
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
    public ResponseEntity<String> signUp(@Valid @RequestBody RegisterRequest registerRequest) throws SignupException {
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
        return ResponseEntity.ok(authService.logIn(loginRequest));
    }

    @PostMapping("/refresh/token")
    public ResponseEntity<AuthenticationResponse> refreshToken(@Valid @RequestBody RefreshTokenRequest refreshTokenRequest) throws KeyRetrievalException, InvalidRefreshTokenException {
        return ResponseEntity.ok(authService.refreshToken(refreshTokenRequest));
    }

    @PostMapping("/forgot-password")
    public ResponseEntity<String> forgotPassword(@Valid @RequestBody Email email) {
        authService.forgotPassword(email.getEmailAddress());

        return ResponseEntity.ok("Email for password reset sent");
    }

    @PostMapping("/reset-password/{resetCode}")
    public ResponseEntity<String> resetPassword(@PathVariable String resetCode, @Valid @RequestBody Password newPassword) throws InvalidVerificationTokenException, OldPasswordException {
        authService.resetPassword(resetCode, newPassword.getPassword());

        return ResponseEntity.ok("Password reset successful");
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logOut(@Valid @RequestBody RefreshTokenRequest refreshTokenRequest) throws InvalidRefreshTokenException {
        authService.logOut(refreshTokenRequest);

        return ResponseEntity.ok("Logged out successfully");
    }
}
