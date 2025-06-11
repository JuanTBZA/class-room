package com.juantirado.virtual_classroom.dto.academic;

import com.juantirado.virtual_classroom.dto.auth.UserResponseDto;

public record StudentResponseDto(
        Long id,
        UserResponseDto userResponseDto,
        String universityHeadquarters,
        String intendedMajor
) {
}
