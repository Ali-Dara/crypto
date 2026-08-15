package org.dara.userservice.service.impl;

import lombok.RequiredArgsConstructor;
import org.dara.cryptosecurity.model.CurrentUser;
import org.dara.cryptosecurity.util.SecurityUtils;
import org.dara.userservice.Exception.UserNotFoundException;
import org.dara.userservice.dto.UserResponseDto;
import org.dara.userservice.mapper.UserMapper;
import org.dara.userservice.model.User;
import org.dara.userservice.repository.UserRepository;
import org.dara.userservice.service.UserService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Override
    public UserResponseDto getCurrentUser() {
        CurrentUser currentUser = SecurityUtils.getCurrentUser();
        UUID userUUID = currentUser.userUuid();
        User user = userRepository.findByUserUUID(userUUID).orElseThrow(() -> new UserNotFoundException(userUUID.toString()));
        return userMapper.UserToUserResponseDto(user);
    }

    @Override
    public List<UserResponseDto> getAllUsers() {
        return List.of();
    }
}
