package com.juantirado.virtual_classroom.dto.academic;

import java.time.LocalDate;

public record SemesterRequestDto(
        String name,
        LocalDate startDate,
        LocalDate endDate

) {
}
