package org.dara.authenticationservice.service;

import org.dara.authenticationservice.model.AuthUser;

public interface JwtService {

    String generateAccessToken(AuthUser user);
    boolean isTokenValid(String token);
    String extractUsername(String token);
}
