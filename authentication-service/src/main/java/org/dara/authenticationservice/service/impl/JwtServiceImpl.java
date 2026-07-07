package org.dara.authenticationservice.service.impl;

import org.dara.authenticationservice.model.AuthUser;
import org.dara.authenticationservice.service.JwtService;
import org.springframework.stereotype.Service;

@Service
public class JwtServiceImpl implements JwtService {

    @Override
    public String generateAccessToken(AuthUser user) {
        return "";
    }

    @Override
    public boolean isTokenValid(String token) {
        return false;
    }

    @Override
    public String extractUsername(String token) {
        return "";
    }
}
