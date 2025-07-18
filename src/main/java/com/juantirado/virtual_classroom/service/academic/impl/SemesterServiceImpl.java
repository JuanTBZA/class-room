package com.juantirado.virtual_classroom.service.academic.impl;

import com.juantirado.virtual_classroom.dto.academic.SemesterRequestDto;
import com.juantirado.virtual_classroom.dto.academic.SemesterResponseDto;
import com.juantirado.virtual_classroom.mapper.academic.SemesterMapper;
import com.juantirado.virtual_classroom.repository.academic.SemesterRepository;
import com.juantirado.virtual_classroom.service.academic.SemesterService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SemesterServiceImpl implements SemesterService {
    private final SemesterRepository semesterRepository;
    private final SemesterMapper semesterMapper;

    @Override
    public List<SemesterResponseDto> getAll(){
        return semesterRepository
                .findAll()
                .stream()
                .map(semesterMapper::toResponseDto).toList();
    }

    @Override
    public SemesterResponseDto findById(Long id){
        return semesterMapper.toResponseDto(semesterRepository.findById(id).orElse(null));
    }

    @Override
    public SemesterResponseDto create(SemesterRequestDto dto){
        return semesterMapper.toResponseDto(semesterRepository.save(semesterMapper.toEntity(dto)));
    }

    @Override
    public SemesterResponseDto update(Long id, SemesterRequestDto requestDto) {
        return semesterRepository.findById(id).map(semester -> {
            semesterMapper.updateEntity(requestDto,semester);
            return semesterMapper.toResponseDto(semester);
        }).orElse(null);
    }



}
