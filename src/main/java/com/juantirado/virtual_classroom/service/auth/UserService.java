package com.juantirado.virtual_classroom.service.auth;

import com.juantirado.virtual_classroom.dto.auth.UserRequestDto;
import com.juantirado.virtual_classroom.dto.auth.UserResponseDto;
import com.juantirado.virtual_classroom.entity.auth.User;

import java.util.List;

public interface UserService {
    List<UserResponseDto> getAllUsers();

    UserResponseDto getById(long id);

    UserResponseDto delete(long id);

    User createTeacherUser(UserRequestDto dto);

    User createStudentUser(UserRequestDto dto);
}
