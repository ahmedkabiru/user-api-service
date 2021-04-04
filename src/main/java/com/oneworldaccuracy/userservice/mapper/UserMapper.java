package com.oneworldaccuracy.userservice.mapper;

import com.oneworldaccuracy.userservice.dto.UserDto;
import com.oneworldaccuracy.userservice.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

/**
 * @project onewa-user-service
 * @Author kabiruahmed on 04/04/2021
 */
@Mapper(componentModel = "spring",unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserMapper {

    User userDTOToUser(UserDto userDto);

    UserDto userToUserDto(User user);

}
