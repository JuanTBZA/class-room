package com.juantirado.virtual_classroom.dto.academic;

import java.math.BigDecimal;

public record ShiftRequestDto (
        String name,
        String modality,
        Long semesterId,
        BigDecimal price
) {
}
