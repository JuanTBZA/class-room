package com.juantirado.virtual_classroom.service.academic.impl;

import com.juantirado.virtual_classroom.dto.academic.ShiftRequestDto;
import com.juantirado.virtual_classroom.dto.academic.ShiftResponseDto;
import com.juantirado.virtual_classroom.entity.academic.Shift;
import com.juantirado.virtual_classroom.mapper.academic.ShiftMapper;
import com.juantirado.virtual_classroom.repository.academic.SemesterRepository;
import com.juantirado.virtual_classroom.repository.academic.ShiftRepository;
import com.juantirado.virtual_classroom.service.academic.ShiftService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class ShiftServiceImpl implements ShiftService {
    private final ShiftRepository shiftRepository;
    private final ShiftMapper shiftMapper;
    private final SemesterRepository semesterRepository;

    @Override
    public List<ShiftResponseDto> getAll(){
        return shiftRepository.findAll().stream().map(shiftMapper::toResponseDto).toList();
    }

    @Override
    public ShiftResponseDto findById(Long id) {
        return shiftRepository.findById(id).map(shiftMapper::toResponseDto).orElse(null);
    }

    @Override
    public ShiftResponseDto create(ShiftRequestDto shiftRequestDto) {
        Shift shift =shiftMapper.toEntity(shiftRequestDto);
        shift.setSemester(semesterRepository.findById(shiftRequestDto.semesterId()).orElse(null));
        return shiftMapper.toResponseDto(shiftRepository.save(shift));
    }

    @Override
    public ShiftResponseDto update(ShiftRequestDto shiftRequestDto, Long id) {
        return shiftRepository.findById(id).map(shift -> {
            shiftMapper.updateEntity(shift,shiftRequestDto);
            shift.setSemester(semesterRepository.findById(id).orElse(null));
            return shiftRepository.save(shift);
        }).map(shiftMapper::toResponseDto)
                .orElse(null);
    }
}
