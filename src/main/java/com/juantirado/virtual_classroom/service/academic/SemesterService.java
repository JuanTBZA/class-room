package com.juantirado.virtual_classroom.service.academic;

import com.juantirado.virtual_classroom.dto.academic.SemesterResponseDto;

import java.util.List;

public interface SemesterService {
    List<SemesterResponseDto> getAll();
}
