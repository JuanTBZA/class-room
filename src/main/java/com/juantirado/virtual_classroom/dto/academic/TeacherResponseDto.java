package com.juantirado.virtual_classroom.dto.academic;

import com.juantirado.virtual_classroom.dto.auth.UserResponseDto;

import java.time.LocalDate;

public record TeacherResponseDto (
        Long id,
        UserResponseDto userResponseDto,
        LocalDate contractDateStart,
        LocalDate contractDateEnd,
        String specialization
) {
}
