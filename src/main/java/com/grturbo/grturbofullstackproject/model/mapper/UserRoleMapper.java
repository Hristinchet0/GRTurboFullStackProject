package com.grturbo.grturbofullstackproject.model.mapper;

import com.grturbo.grturbofullstackproject.model.dto.UserRoleDto;
import com.grturbo.grturbofullstackproject.model.entity.UserRole;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserRoleMapper {

    UserRoleDto userRoleEntityToUserRoleDto(UserRole userRole);
}