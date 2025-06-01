package com.juantirado.virtual_classroom.dto.academic;

import java.time.LocalDate;

public record TeacherResponseDTo(
        LocalDate contractDateStart,
        LocalDate contractDateEnd,
        String specialization
) {
}
