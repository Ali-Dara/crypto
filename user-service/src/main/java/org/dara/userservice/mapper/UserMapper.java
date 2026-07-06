package org.dara.userservice.mapper;

import org.dara.userservice.dto.UserRequestDto;
import org.dara.userservice.dto.UserResponseDto;
import org.dara.userservice.model.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {
    public User UserReqestDtoToUser(UserRequestDto userRequestDto);
    public UserResponseDto UserToUserResponseDto(User user);
}
