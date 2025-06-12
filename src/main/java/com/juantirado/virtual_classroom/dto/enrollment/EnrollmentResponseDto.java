package com.juantirado.virtual_classroom.dto.enrollment;

import com.juantirado.virtual_classroom.entity.enums.EnrollmentStatus;
import com.juantirado.virtual_classroom.entity.enums.PaymentMethod;
import com.juantirado.virtual_classroom.entity.enums.PaymentStatus;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public class EnrollmentResponseDto {
    public Long id;
    public Long studentId;
    public EnrollmentStatus enrollmentStatus;
    public PaymentStatus paymentStatus;
    public PaymentMethod paymentMethod;
    public String fileVoucherUrl;
    public BigDecimal totalAmount;
    public LocalDateTime enrollmentDate;
    public List<EnrollmentDetailResponseDto> details;
}
