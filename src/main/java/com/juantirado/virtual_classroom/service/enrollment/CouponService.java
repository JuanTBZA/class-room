package com.juantirado.virtual_classroom.service.enrollment;

import com.juantirado.virtual_classroom.dto.enrollment.CouponPatchRequestDto;
import com.juantirado.virtual_classroom.dto.enrollment.CouponRequestDto;
import com.juantirado.virtual_classroom.dto.enrollment.CouponResponseDto;

import java.util.List;

public interface CouponService {
    CouponResponseDto create(CouponRequestDto dto);

    CouponResponseDto getByCode(String code);

    List<CouponResponseDto> getAll();

    void delete(Long id);

    CouponResponseDto patch(Long id, CouponPatchRequestDto dto);

}
