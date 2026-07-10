package org.dara.authenticationservice.service;

import org.dara.authenticationservice.model.AuthUser;

import java.util.UUID;

public interface JwtService {

    String generateAccessToken(AuthUser user);
    boolean isTokenValid(String token);
    boolean isTokenExpired(String token);
    String extractUsername(String token);
    UUID extractUserId(String token);

}
