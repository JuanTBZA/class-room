package com.juantirado.virtual_classroom.service.enrollment;

import com.juantirado.virtual_classroom.dto.enrollment.EnrollmentDetailResponseDto;

import java.util.List;

public interface EnrollmentDetailService {
    List<EnrollmentDetailResponseDto> getByEnrollmentId(Long enrollmentId);
}
