package com.juantirado.virtual_classroom.dto.academic;

import com.juantirado.virtual_classroom.dto.auth.RegisterRequestDto;

public record StudentRequestDto (
    RegisterRequestDto registerRequestDto,
    String universityHeadquarters,
    String intended_major
){
}
