package org.dara.authenticationservice.service;

import org.dara.authenticationservice.dto.AuthResponse;
import org.dara.authenticationservice.dto.RefreshTokenRequest;
import org.dara.authenticationservice.model.AuthUser;
import org.dara.authenticationservice.model.RefreshToken;

import java.util.Optional;

public interface RefreshTokenService {

    String create(AuthUser user);
    Optional<RefreshToken> findByToken(String token);
    void delete(RefreshToken token);
    void deleteByAuthUser(AuthUser user);
    RefreshToken verify(String refreshToken) throws Exception;
    AuthResponse refresh(RefreshTokenRequest request) throws Exception;
    void revoke(String refreshToken);
}
