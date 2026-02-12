package com.juantirado.virtual_classroom.dto.security;

import com.juantirado.virtual_classroom.dto.auth.UserResponseDto;

public record AdminResponseDto(
        Long id,
        UserResponseDto userResponseDto,
        Long createdByAdminId,
        Boolean isMaster,
        String department
) {
}
