package org.acme.service;

import io.smallrye.jwt.build.Jwt;
import io.smallrye.jwt.build.JwtClaimsBuilder;

import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class AuthService {

    public String generateToken(String username) {
        JwtClaimsBuilder claims = Jwt.issuer("garden-of-eden")
                .preferredUserName(username)
                .groups("USER")
                .issuedAt(System.currentTimeMillis())
                .expiresAt(System.currentTimeMillis() + 3600);

        return claims.sign();
    }
}

