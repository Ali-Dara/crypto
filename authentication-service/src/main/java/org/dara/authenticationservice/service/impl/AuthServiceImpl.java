package org.dara.authenticationservice.service.impl;

import lombok.RequiredArgsConstructor;
import org.dara.authenticationservice.Exception.EmailAlreadyExistsException;
import org.dara.authenticationservice.Exception.UsernameAlreadyExistsException;
import org.dara.authenticationservice.dto.AuthResponse;
import org.dara.authenticationservice.dto.LoginRequest;
import org.dara.authenticationservice.dto.RegisterRequest;
import org.dara.authenticationservice.mapper.AuthUserMapper;
import org.dara.authenticationservice.model.AuthUser;
import org.dara.authenticationservice.model.CustomUserDetails;
import org.dara.authenticationservice.service.*;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final AuthenticationManager authenticationManager;
    private final AuthUserMapper authUserMapper;
    private final AuthUserService authUserService;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final RefreshTokenService refreshTokenService;
    private final RoleService roleService;



    @Override
    public AuthResponse login(LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.username(), loginRequest.password()));
        if(authentication.isAuthenticated()) {
            CustomUserDetails userDetails = (CustomUserDetails)authentication.getDetails();
            return authUserMapper.authUserToAuthResponse(userDetails.getAuthUser());
        }else
            return null;
    }

    @Override
    public AuthResponse register(RegisterRequest registerRequest) {

        if(authUserService.existsByEmail(registerRequest.email()))
            throw new EmailAlreadyExistsException(registerRequest.email());
        if(authUserService.existsByUsername(registerRequest.username()))
            throw new UsernameAlreadyExistsException(registerRequest.username());

        String password = passwordEncoder.encode(registerRequest.password());
        AuthUser user = authUserMapper.registerRequestToAuthUser(registerRequest);
        user.setPassword(password);

        roleService.findRoleByRoleName("ROLE_USER").ifPresent(user::addRole);

        authUserService.save(user);
        return authUserMapper.authUserToAuthResponse(user);
    }
}
