package org.dara.authenticationservice.service;

import org.dara.authenticationservice.dto.AuthResponse;
import org.dara.authenticationservice.dto.LoginRequest;
import org.dara.authenticationservice.dto.RegisterRequest;
import org.dara.authenticationservice.model.AuthUser;

import java.util.Optional;
import java.util.UUID;

public interface AuthUserService {

    public AuthUser  save(AuthUser authUser);
    public Optional<AuthUser> findByUsername(String username);
    public Optional<AuthUser> findByUserUuid(UUID uuid);
    public Optional<AuthUser> findByEmail(String email);
    public boolean existsByUsername(String username);
    public boolean existsByEmail(String email);
    public Optional<AuthUser> findByUsernameWithAuthorities(String username);
}
