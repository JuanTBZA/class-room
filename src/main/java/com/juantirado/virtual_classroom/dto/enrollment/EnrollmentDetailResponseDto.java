package com.juantirado.virtual_classroom.dto.enrollment;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class EnrollmentDetailResponseDto {
    public Long shiftId;
    public Long couponId;
    public BigDecimal basePrice;
    public BigDecimal appliedDiscount;
    public BigDecimal total;
    public LocalDateTime appliedAt;
}
