package com.juantirado.virtual_classroom.service.academic.impl;

import com.juantirado.virtual_classroom.dto.academic.SemesterResponseDto;
import com.juantirado.virtual_classroom.mapper.academic.SemesterMapper;
import com.juantirado.virtual_classroom.repository.academic.SemesterRepository;
import com.juantirado.virtual_classroom.service.academic.SemesterService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor()
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

}
