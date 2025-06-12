package com.juantirado.virtual_classroom.service.enrollment.impl;

import com.juantirado.virtual_classroom.dto.enrollment.CouponPatchRequestDto;
import com.juantirado.virtual_classroom.dto.enrollment.CouponRequestDto;
import com.juantirado.virtual_classroom.dto.enrollment.CouponResponseDto;
import com.juantirado.virtual_classroom.entity.enrollment.Coupon;
import com.juantirado.virtual_classroom.mapper.enrollment.CouponMapper;
import com.juantirado.virtual_classroom.repository.enrollment.CouponRepository;
import com.juantirado.virtual_classroom.service.enrollment.CouponService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CouponServiceImpl implements CouponService {

    private final CouponRepository couponRepository;
    private final CouponMapper couponMapper;

    @Override
    public CouponResponseDto create(CouponRequestDto dto) {
        Coupon coupon = couponMapper.toEntity(dto);
        return couponMapper.toResponseDto(couponRepository.save(coupon));
    }

    @Override
    public CouponResponseDto getByCode(String code) {
        return couponRepository.findByCode(code)
                .map(couponMapper::toResponseDto)
                .orElseThrow(() -> new RuntimeException("Coupon with code " + code + " not found"));
    }

    @Override
    public List<CouponResponseDto> getAll() {
        return couponRepository.findAll().stream()
                .map(couponMapper::toResponseDto)
                .toList();
    }

    @Override
    public void delete(Long id) {
        couponRepository.deleteById(id);
    }

    @Override
    public CouponResponseDto patch(Long id, CouponPatchRequestDto dto) {
        Coupon coupon = couponRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Coupon not found with id: " + id));

        if (dto.description() != null) {
            coupon.setDescription(dto.description());
        }

        if (dto.maxUses() != null && dto.maxUses() >= coupon.getUsedCount()) {
            coupon.setMaxUses(dto.maxUses());
        }

        if (dto.validUntil() != null) {
            coupon.setValidUntil(dto.validUntil());
        }

        if (dto.active() != null) {
            coupon.setActive(dto.active());
        }

        return couponMapper.toResponseDto(couponRepository.save(coupon));
    }

}
