package com.juantirado.virtual_classroom.mapper.enrollment;

import com.juantirado.virtual_classroom.dto.enrollment.CouponRequestDto;
import com.juantirado.virtual_classroom.dto.enrollment.CouponResponseDto;
import com.juantirado.virtual_classroom.entity.enrollment.Coupon;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CouponMapper {

    Coupon toEntity(CouponRequestDto dto);

    CouponResponseDto toResponseDto(Coupon coupon);
}
