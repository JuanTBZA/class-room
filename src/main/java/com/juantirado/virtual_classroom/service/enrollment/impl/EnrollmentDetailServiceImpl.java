package com.juantirado.virtual_classroom.service.enrollment.impl;

import com.juantirado.virtual_classroom.dto.enrollment.EnrollmentDetailResponseDto;
import com.juantirado.virtual_classroom.entity.enrollment.EnrollmentDetail;
import com.juantirado.virtual_classroom.mapper.enrollment.EnrollmentDetailMapper;
import com.juantirado.virtual_classroom.repository.enrollment.EnrollmentDetailRepository;
import com.juantirado.virtual_classroom.service.enrollment.EnrollmentDetailService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EnrollmentDetailServiceImpl implements EnrollmentDetailService {

    private final EnrollmentDetailRepository detailRepository;
    private final EnrollmentDetailMapper detailMapper;

    @Override
    public List<EnrollmentDetailResponseDto> getByEnrollmentId(Long enrollmentId) {
        List<EnrollmentDetail> details = detailRepository.findByEnrollmentId(enrollmentId);
        return details.stream()
                .map(detailMapper::toResponseDto)
                .toList();
    }
}
