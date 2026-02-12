package com.juantirado.virtual_classroom.service.auth;

import com.juantirado.virtual_classroom.dto.PaginatedResponseDto;
import com.juantirado.virtual_classroom.dto.auth.UserRequestDto;
import com.juantirado.virtual_classroom.dto.auth.UserResponseDto;
import com.juantirado.virtual_classroom.entity.auth.User;

import java.util.List;

public interface UserService {
    List<UserResponseDto> getAllUsers();

    PaginatedResponseDto<UserResponseDto> getUsersByPage(
            String filtro, int page, int size, String orderBy, String orderDir, Boolean enabled
    );

    UserResponseDto getUserById(long id);

    User createUserWithRole(UserRequestDto dto, String roleName);

    User updateUser(Long userId, UserRequestDto dto);

    void deleteUser(Long userId);







}
