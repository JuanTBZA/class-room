package com.juantirado.virtual_classroom.model.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class CreateSemesterRequestDto {

    private String name;

    @NotNull
    private LocalDateTime starDate;

    @NotNull
    private LocalDateTime endDate;
}
