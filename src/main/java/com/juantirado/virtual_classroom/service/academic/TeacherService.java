package com.juantirado.virtual_classroom.service.academic;

import com.juantirado.virtual_classroom.dto.academic.TeacherRequestDto;
import com.juantirado.virtual_classroom.dto.academic.TeacherResponseDto;
import jakarta.transaction.Transactional;

import java.util.List;
import java.util.Optional;

public interface TeacherService {
    List<TeacherResponseDto> getAll();

    TeacherResponseDto getById(Long id);

    Optional<TeacherResponseDto> getByUserId(Long userId);

    TeacherResponseDto createTeacher(TeacherRequestDto teacherRequestDto);

    @Transactional
    TeacherResponseDto updateTeacher(Long id, TeacherRequestDto teacherRequestDto);

    long getTotalTeacherCount();

    @Transactional
    void deleteTeacher(Long id);
}
