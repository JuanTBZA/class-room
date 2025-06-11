package com.juantirado.virtual_classroom.dto.academic;

import com.juantirado.virtual_classroom.dto.auth.UserRequestDto;

public record StudentRequestDto(
        UserRequestDto userRequestDto,
        String universityHeadquarters,
        String intendedMajor
) {
}
