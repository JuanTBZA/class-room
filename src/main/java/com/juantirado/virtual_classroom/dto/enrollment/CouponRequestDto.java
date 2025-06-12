package com.juantirado.virtual_classroom.dto.enrollment;

import com.juantirado.virtual_classroom.entity.enums.CouponType;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record CouponRequestDto(
        String code,
        String description,
        CouponType type,
        BigDecimal amount,
        Integer maxUses,
        LocalDateTime validFrom,
        LocalDateTime validUntil,
        Boolean active
) {}
