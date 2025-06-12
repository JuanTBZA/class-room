package com.juantirado.virtual_classroom.dto.enrollment;

import com.juantirado.virtual_classroom.entity.enums.EnrollmentStatus;
import com.juantirado.virtual_classroom.entity.enums.PaymentMethod;
import com.juantirado.virtual_classroom.entity.enums.PaymentStatus;

import java.util.List;

public class EnrollmentRequestDto {
    public Long studentId;
    public String fileVoucherUrl;
    public EnrollmentStatus enrollmentStatus;
    public PaymentStatus paymentStatus;
    public PaymentMethod paymentMethod;
    public List<EnrollmentDetailRequestDto> details;
}
