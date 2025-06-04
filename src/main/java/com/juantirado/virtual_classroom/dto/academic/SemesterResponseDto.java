package com.juantirado.virtual_classroom.dto.academic;

public record SemesterResponseDto (
        Long id,
        String name,
        String year,
        String state
)
{
}
