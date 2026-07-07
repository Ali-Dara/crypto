package org.dara.authenticationservice.service;

import org.dara.authenticationservice.model.AuthUser;
import org.dara.authenticationservice.model.RefreshToken;

import java.util.Optional;

public interface RefreshTokenService {

    RefreshToken create(AuthUser user);
    Optional<RefreshToken> findByToken(String token);
    void delete(RefreshToken token);
    void deleteByAuthUser(AuthUser user);
}
