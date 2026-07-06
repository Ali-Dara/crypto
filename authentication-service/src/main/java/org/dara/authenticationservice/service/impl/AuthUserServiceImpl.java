package org.dara.authenticationservice.service.impl;

import lombok.RequiredArgsConstructor;
import org.dara.authenticationservice.dto.AuthResponse;
import org.dara.authenticationservice.dto.LoginRequest;
import org.dara.authenticationservice.mapper.AuthUserMapper;
import org.dara.authenticationservice.model.AuthUser;
import org.dara.authenticationservice.model.CustomUserDetails;
import org.dara.authenticationservice.repository.AuthUserRepository;
import org.dara.authenticationservice.service.AuthUserService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthUserServiceImpl implements AuthUserService {

    private final AuthUserRepository authUserRepository;
    private final AuthenticationManager authenticationManager;
    private final AuthUserMapper authUserMapper;

    @Override
    public AuthUser save(AuthUser authUser) {
        return authUserRepository.save(authUser);
    }

    @Override
    public Optional<AuthUser> findByUsername(String username) {
        return authUserRepository.findByUsername(username);
    }

    @Override
    public Optional<AuthUser> findByEmail(String email) {
        return authUserRepository.findByEmail(email);
    }

    @Override
    public boolean existsByUsername(String username) {
        return authUserRepository.existsByUsername(username);
    }

    @Override
    public boolean existsByEmail(String email) {
        return authUserRepository.existsByEmail(email);
    }

    @Override
    public AuthResponse login(LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.username(), loginRequest.password()));
        if(authentication.isAuthenticated()) {
            CustomUserDetails userDetails = (CustomUserDetails)authentication.getDetails();
            return authUserMapper.toAuthResponse(userDetails.getAuthUser());
        }else
            return null;
    }
}
