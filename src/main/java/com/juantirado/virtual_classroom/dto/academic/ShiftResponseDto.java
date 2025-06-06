package com.juantirado.virtual_classroom.dto.academic;

import java.math.BigDecimal;

public record ShiftResponseDto (
        Long id,
        String name,
        String modality,
        String semesterName,
        BigDecimal price
){

}
