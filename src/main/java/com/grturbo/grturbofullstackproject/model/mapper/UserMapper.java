package com.grturbo.grturbofullstackproject.model.mapper;

import com.grturbo.grturbofullstackproject.model.dto.UserDto;
import com.grturbo.grturbofullstackproject.model.dto.UserEditDto;
import com.grturbo.grturbofullstackproject.model.dto.UserRegisterDto;
import com.grturbo.grturbofullstackproject.model.entity.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {

    User userDtoToUserEntity(UserRegisterDto userRegisterDto);

    UserDto userEntityToUserDto(User user);

    User userEditDtoToUserEntity(UserEditDto userEditDto);
}