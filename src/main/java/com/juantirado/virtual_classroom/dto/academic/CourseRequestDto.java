package com.juantirado.virtual_classroom.dto.academic;

import jakarta.validation.constraints.NotEmpty;

public record CourseRequestDto (
        @NotEmpty(message = "El campo nombre es obligatorio")
        String name,
        String description
){

}
