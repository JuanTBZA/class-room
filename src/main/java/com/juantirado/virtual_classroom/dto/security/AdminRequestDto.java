package com.juantirado.virtual_classroom.dto.security;

import com.juantirado.virtual_classroom.dto.auth.UserRequestDto;

public record AdminRequestDto(
        UserRequestDto userRequestDto,
        Long createdByAdminId,
        Boolean isMaster,
        String department
) {
}
