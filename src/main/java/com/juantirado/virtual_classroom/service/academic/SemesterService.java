package com.juantirado.virtual_classroom.service.academic;

import com.juantirado.virtual_classroom.dto.academic.SemesterRequestDto;
import com.juantirado.virtual_classroom.dto.academic.SemesterResponseDto;

import java.util.List;

public interface SemesterService {
    List<SemesterResponseDto> getAll();

    SemesterResponseDto findById(Long id);

    SemesterResponseDto create(SemesterRequestDto dto);

    SemesterResponseDto update(Long id, SemesterRequestDto requestDto);
}
