package com.juantirado.virtual_classroom.mapper.auth;

import com.juantirado.virtual_classroom.dto.auth.UserRequestDto;
import com.juantirado.virtual_classroom.dto.auth.UserResponseDto;
import com.juantirado.virtual_classroom.entity.auth.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface UserMapper {

    @Mapping(source = "role.name", target = "role")
    public UserResponseDto toResponseDto(User user);

    public User toEntity(UserRequestDto requestDto);


}
