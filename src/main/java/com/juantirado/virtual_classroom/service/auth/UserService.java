package com.juantirado.virtual_classroom.service.auth;

import com.juantirado.virtual_classroom.dto.auth.UserRequestDto;
import com.juantirado.virtual_classroom.dto.auth.UserResponseDto;

import java.util.List;

public interface UserService {
    List<UserResponseDto> getAllUsers();

    UserResponseDto getById(long id);

    UserResponseDto create(UserRequestDto userRequestDto);

    UserResponseDto update(long id, UserRequestDto userRequestDto);
}
