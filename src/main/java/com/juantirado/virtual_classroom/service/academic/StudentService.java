package com.juantirado.virtual_classroom.service.academic;

import com.juantirado.virtual_classroom.dto.academic.StudentRequestDto;
import com.juantirado.virtual_classroom.dto.academic.StudentResponseDto;
import jakarta.transaction.Transactional;

import java.util.List;

public interface StudentService {
    List<StudentResponseDto> getAll();

    StudentResponseDto getById(Long id);

    @Transactional
    StudentResponseDto createStudent(StudentRequestDto studentRequestDto);

    long getActiveStudentCount();
}
