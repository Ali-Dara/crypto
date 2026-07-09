package org.dara.authenticationservice.service;

import org.dara.authenticationservice.Exception.UserNotFoundException;
import org.dara.authenticationservice.dto.AuthResponse;
import org.dara.authenticationservice.dto.LoginRequest;
import org.dara.authenticationservice.dto.RegisterRequest;

public interface AuthService {

    AuthResponse login(LoginRequest loginRequest) throws UserNotFoundException;
    AuthResponse register(RegisterRequest registerRequest);
}
