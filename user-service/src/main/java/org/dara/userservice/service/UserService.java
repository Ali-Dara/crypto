package org.dara.userservice.service;

import org.dara.cryptoevent.Dto.AuthUserRegisteredEvent;
import org.dara.userservice.dto.UserRequestDto;
import org.dara.userservice.dto.UserResponseDto;

import java.util.List;

public interface UserService {

    void createUserByRegisterEvent(AuthUserRegisteredEvent event);
    UserResponseDto getCurrentUser();
    List<UserResponseDto> getAllUsers();
    UserResponseDto updateCurrentUser(UserRequestDto userRequestDto);
}
