package org.dara.userservice.service.impl;

import lombok.RequiredArgsConstructor;
import org.dara.cryptoevent.Dto.AuthUserRegisteredEvent;
import org.dara.cryptosecurity.model.CurrentUser;
import org.dara.cryptosecurity.util.SecurityUtils;
import org.dara.userservice.Exception.UserNotFoundException;
import org.dara.userservice.dto.UserRequestDto;
import org.dara.userservice.dto.UserResponseDto;
import org.dara.userservice.mapper.UserMapper;
import org.dara.userservice.model.User;
import org.dara.userservice.repository.UserRepository;
import org.dara.userservice.service.UserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Transactional
    @Override
    public void createUserByRegisterEvent(AuthUserRegisteredEvent event) {

        if(userRepository.existsByUserUUID(event.userUUID()))
            return;

        User newUser = new User();
        newUser.setUserUUID(event.userUUID());
        newUser.setUserName(event.username());
        newUser.setEmail(event.email());
        userRepository.save(newUser);
    }

    @Override
    public UserResponseDto getCurrentUser() {
        CurrentUser currentUser = SecurityUtils.getCurrentUser();
        UUID userUUID = currentUser.userUuid();
        User user = userRepository.findByUserUUID(userUUID).orElseThrow(() -> new UserNotFoundException(userUUID.toString()));
        return userMapper.UserToUserResponseDto(user);
    }

    @Override
    public List<UserResponseDto> getAllUsers() {
        return userRepository.findAll().stream().map(userMapper::UserToUserResponseDto).toList();
    }

    @Transactional
    @Override
    public UserResponseDto updateCurrentUser(UserRequestDto userRequestDto) {
        UUID userUUID = SecurityUtils.getCurrentUser().userUuid();

        User user = userRepository.findByUserUUID(userUUID).orElseThrow(() -> new UserNotFoundException(userUUID.toString()));

        user.setFullName(userRequestDto.fullName());
        user.setPhone(userRequestDto.phone());
        user.setBirthDate(userRequestDto.birthDate());

        return userMapper.UserToUserResponseDto(user);
    }
}
