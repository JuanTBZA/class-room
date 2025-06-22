package com.juantirado.virtual_classroom.dto.academic;

import com.juantirado.virtual_classroom.dto.validators.AlphaNumericWithSpaces;
import jakarta.validation.constraints.NotEmpty;

public record CourseRequestDto (
        @NotEmpty(message = "El campo nombre es obligatorio")
        @AlphaNumericWithSpaces(message = "Solo numeros, espacios y letras")
        String name,
        String description
){

}
