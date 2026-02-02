package com.juantirado.virtual_classroom.service.security;

import com.juantirado.virtual_classroom.dto.auth.UserRequestDto;
import com.juantirado.virtual_classroom.dto.auth.UserResponseDto;

public interface AdminService {
    UserResponseDto updateAdmin(long id, UserRequestDto dto);
}

