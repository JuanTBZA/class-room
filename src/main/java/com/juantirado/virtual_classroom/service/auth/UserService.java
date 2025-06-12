package com.juantirado.virtual_classroom.service.auth;

import com.juantirado.virtual_classroom.dto.PaginatedResponseDto;
import com.juantirado.virtual_classroom.dto.auth.UserRequestDto;
import com.juantirado.virtual_classroom.dto.auth.UserResponseDto;
import com.juantirado.virtual_classroom.entity.auth.User;

import java.util.List;

public interface UserService {
    List<UserResponseDto> getAllUsers();

    PaginatedResponseDto<UserResponseDto> getUsersByPage(
            String filtro, int page, int size, String orderBy, String orderDir
    );

    UserResponseDto getById(long id);

    UserResponseDto delete(long id);

    User createTeacherUser(UserRequestDto dto);

    User createStudentUser(UserRequestDto dto);
}
