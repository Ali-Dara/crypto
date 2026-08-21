package org.dara.authenticationservice.service;

import org.dara.authenticationservice.model.EmailVerificationToken;

import java.util.UUID;

public interface EmailVerificationTokenService {

    String createToken(UUID userUUId);
    EmailVerificationToken verifyToken(String token);
    void deleteToken(UUID userUUId);
}
