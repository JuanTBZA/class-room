package com.juantirado.virtual_classroom.service.academic;

import com.juantirado.virtual_classroom.dto.academic.TeacherRequestDto;
import com.juantirado.virtual_classroom.dto.academic.TeacherResponseDto;
import jakarta.transaction.Transactional;

import java.util.List;

public interface TeacherService {
    List<TeacherResponseDto> getAll();

    TeacherResponseDto getById(Long id);

    TeacherResponseDto createTeacher(TeacherRequestDto teacherRequestDto);

    long getTotalTeacherCount();

    @Transactional
    void deleteTeacher(Long id);
}
