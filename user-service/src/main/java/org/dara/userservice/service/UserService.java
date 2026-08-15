package org.dara.userservice.service;

import org.dara.userservice.dto.UserResponseDto;

import java.util.List;

public interface UserService {

    UserResponseDto getCurrentUser();
    List<UserResponseDto> getAllUsers();
}
