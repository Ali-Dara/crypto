package org.dara.authenticationservice.mapper;

import org.dara.authenticationservice.dto.AuthResponse;
import org.dara.authenticationservice.dto.RegisterRequest;
import org.dara.authenticationservice.model.AuthUser;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AuthUserMapper {

    public AuthResponse authUserToAuthResponse(AuthUser authUser);
    public AuthUser registerRequestToAuthUser(RegisterRequest registerRequest);
}
