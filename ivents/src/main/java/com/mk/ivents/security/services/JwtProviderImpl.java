package com.mk.ivents.security.services;

import com.mk.ivents.security.exceptions.KeyRetrievalException;
import com.mk.ivents.security.exceptions.KeyStoreLoadingException;
import com.mk.ivents.security.interfaces.JwtProvider;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.io.InputStream;
import java.security.*;
import java.security.cert.CertificateException;
import java.time.Instant;
import java.util.Date;

import static io.jsonwebtoken.Jwts.parserBuilder;

@Service
public class JwtProviderImpl implements JwtProvider {
    private KeyStore keyStore;

    @Value("${jwt.expirationtime}")
    private Long jwtExpirationTimeInMillis;

    @PostConstruct
    private void init() {
        try {
            keyStore = KeyStore.getInstance("JKS");
            InputStream resourceAsStream = getClass().getResourceAsStream("/ivents.jks");
            keyStore.load(resourceAsStream, "ivents".toCharArray());
        } catch (KeyStoreException | CertificateException | NoSuchAlgorithmException | IOException e) {
            throw new KeyStoreLoadingException("Exception occurred while loading keystore");
        }
    }

    @Override
    public String generateToken(Authentication authentication) throws KeyRetrievalException {
        User principal = (User) authentication.getPrincipal();

        return Jwts.builder()
                .setSubject(principal.getUsername())
                .setIssuedAt(Date.from(Instant.now()))
                .signWith(getPrivateKey())
                .setExpiration(Date.from(Instant.now().plusMillis(jwtExpirationTimeInMillis)))
                .compact();
    }

    @Override
    public String generateTokenWithUserName(String username) throws KeyRetrievalException {
        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(Date.from(Instant.now()))
                .signWith(getPrivateKey())
                .setExpiration(Date.from(Instant.now().plusMillis(jwtExpirationTimeInMillis)))
                .compact();
    }

    private PrivateKey getPrivateKey() throws KeyRetrievalException {
        try {
            return (PrivateKey) keyStore.getKey("authkey", "authkey".toCharArray());
        } catch (KeyStoreException | NoSuchAlgorithmException | UnrecoverableKeyException e) {
            throw new KeyRetrievalException("Exception occurred while retrieving private key from keystore");
        }
    }

    @Override
    public boolean validateToken(String jwt) throws KeyRetrievalException {
        parserBuilder()
                .setSigningKey(getPublicKey())
                .build()
                .parseClaimsJws(jwt);

        return true;
    }

    private PublicKey getPublicKey() throws KeyRetrievalException {
        try {
            return keyStore.getCertificate("authkey").getPublicKey();
        } catch (KeyStoreException keyStoreException) {
            throw new KeyRetrievalException("Exception occurred while retrieving public key from keystore");
        }
    }

    @Override
    public String getUsernameFromJwt(String token) throws KeyRetrievalException {
        Claims claims = parserBuilder()
                .setSigningKey(getPublicKey())
                .build()
                .parseClaimsJws(token)
                .getBody();

        return claims.getSubject();
    }

    @Override
    public Long getJwtExpirationTimeInMillis() {
        return jwtExpirationTimeInMillis;
    }
}
