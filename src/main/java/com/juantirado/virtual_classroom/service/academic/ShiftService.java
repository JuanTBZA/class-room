package com.juantirado.virtual_classroom.service.academic;

import com.juantirado.virtual_classroom.dto.academic.ShiftRequestDto;
import com.juantirado.virtual_classroom.dto.academic.ShiftResponseDto;

import java.util.List;

public interface ShiftService {
    List<ShiftResponseDto> getAll();

    ShiftResponseDto findById(Long id);

    ShiftResponseDto create(ShiftRequestDto shiftRequestDto);

    ShiftResponseDto update(ShiftRequestDto shiftRequestDto, Long id);
}
