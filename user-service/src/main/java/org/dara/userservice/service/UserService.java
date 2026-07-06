package org.dara.userservice.service;

import org.dara.userservice.dto.UserRequestDto;
import org.dara.userservice.dto.UserResponseDto;
import org.dara.userservice.mapper.UserMapper;
import org.dara.userservice.model.User;
import org.dara.userservice.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    public UserService(UserRepository userRepository, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    public List<UserResponseDto> getAllUsers() {
        List<User> users = userRepository.findAll();
        return users.stream().map(userMapper::UserToUserResponseDto).collect(Collectors.toList());
    }

    public UserResponseDto registerUser(UserRequestDto userRequestDto) {
        User requsetUser = userMapper.UserReqestDtoToUser(userRequestDto);
        return userMapper.UserToUserResponseDto(userRepository.save(requsetUser));
    }
}
