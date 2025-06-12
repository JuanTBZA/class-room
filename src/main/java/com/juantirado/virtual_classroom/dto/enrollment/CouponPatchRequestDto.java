package com.juantirado.virtual_classroom.dto.enrollment;

import java.time.LocalDateTime;

public record CouponPatchRequestDto(
        String description,
        Integer maxUses,
        LocalDateTime validUntil,
        Boolean active
) {}
