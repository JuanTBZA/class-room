package com.juantirado.virtual_classroom.service.enrollment;

import com.juantirado.virtual_classroom.dto.enrollment.EnrollmentRequestDto;
import com.juantirado.virtual_classroom.dto.enrollment.EnrollmentResponseDto;
import jakarta.transaction.Transactional;

import java.util.List;

public interface EnrollmentService {
    @Transactional
    EnrollmentResponseDto create(EnrollmentRequestDto dto);

    List<EnrollmentResponseDto> getAll();

    long getTodayEnrollmentCount();
}
