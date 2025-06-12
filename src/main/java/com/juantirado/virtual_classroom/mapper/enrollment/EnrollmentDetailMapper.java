package com.juantirado.virtual_classroom.mapper.enrollment;

import com.juantirado.virtual_classroom.dto.enrollment.EnrollmentDetailResponseDto;
import com.juantirado.virtual_classroom.entity.enrollment.EnrollmentDetail;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface EnrollmentDetailMapper {

    @Mapping(source = "shift.id", target = "shiftId")
    @Mapping(source = "coupon.id", target = "couponId")
    EnrollmentDetailResponseDto toResponseDto(EnrollmentDetail detail);
}
