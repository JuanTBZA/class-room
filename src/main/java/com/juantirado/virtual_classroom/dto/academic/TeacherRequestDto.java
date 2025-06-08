package com.juantirado.virtual_classroom.dto.academic;

import com.juantirado.virtual_classroom.dto.auth.UserRequestDto;

import java.time.LocalDate;

public record TeacherRequestDto(
        UserRequestDto userRequestDto,
        LocalDate contractDateStart,
        LocalDate contractDateEnd,
        String specialization

){
}
