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

    @Mapping(target = "role", ignore = true)
    public User toEntity(UserRequestDto requestDto);

    @Mapping(target = "id", ignore = true )
    @Mapping(target = "role", ignore = true)
    @Mapping(target = "password", ignore = true)
    public void entityToUpdate(UserRequestDto requestDto, @MappingTarget User user);

}
