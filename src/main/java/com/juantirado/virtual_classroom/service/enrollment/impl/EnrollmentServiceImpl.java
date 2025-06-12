package com.juantirado.virtual_classroom.service.enrollment.impl;

import com.juantirado.virtual_classroom.dto.enrollment.*;
import com.juantirado.virtual_classroom.entity.academic.Shift;
import com.juantirado.virtual_classroom.entity.academic.Student;
import com.juantirado.virtual_classroom.entity.enrollment.*;
import com.juantirado.virtual_classroom.mapper.enrollment.EnrollmentMapper;
import com.juantirado.virtual_classroom.repository.academic.ShiftRepository;
import com.juantirado.virtual_classroom.repository.academic.StudentRepository;
import com.juantirado.virtual_classroom.repository.enrollment.CouponRepository;
import com.juantirado.virtual_classroom.repository.enrollment.EnrollmentDetailRepository;
import com.juantirado.virtual_classroom.repository.enrollment.EnrollmentRepository;
import com.juantirado.virtual_classroom.service.enrollment.EnrollmentService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class EnrollmentServiceImpl implements EnrollmentService {

    private final EnrollmentRepository enrollmentRepository;
    private final EnrollmentDetailRepository detailRepository;
    private final StudentRepository studentRepository;
    private final ShiftRepository shiftRepository;
    private final CouponRepository couponRepository;
    private final EnrollmentMapper enrollmentMapper;

    @Transactional
    @Override
    public EnrollmentResponseDto create(EnrollmentRequestDto dto) {
        Student student = studentRepository.findById(dto.studentId)
                .orElseThrow(() -> new RuntimeException("Student not found"));

        Enrollment enrollment = Enrollment.builder()
                .student(student)
                .fileVoucherUrl(dto.fileVoucherUrl)
                .paymentMethod(dto.paymentMethod)
                .enrollmentStatus(dto.enrollmentStatus)
                .paymentStatus(dto.paymentStatus)
                .enrollmentDate(LocalDateTime.now())
                .build();

        List<EnrollmentDetail> details = new ArrayList<>();
        BigDecimal totalGeneral = BigDecimal.ZERO;

        for (EnrollmentDetailRequestDto d : dto.details) {
                Shift shift = shiftRepository.findById(d.shiftId)
                    .orElseThrow(() -> new RuntimeException("Shift not found"));

            BigDecimal base = shift.getPrice();
            BigDecimal discount = BigDecimal.ZERO;
            Coupon coupon = null;

            if (d.couponId != null) {
                coupon = couponRepository.findById(d.couponId)
                        .filter(c -> c.getActive() && c.getValidUntil().isAfter(LocalDateTime.now()))
                        .orElseThrow(() -> new RuntimeException("Invalid or expired coupon"));

                if (coupon.getUsedCount() >= coupon.getMaxUses()) {
                    throw new RuntimeException("Coupon usage limit reached");
                }

                if (coupon.getType().name().equals("PERCENTAGE")) {
                    discount = base.multiply(coupon.getAmount()).divide(BigDecimal.valueOf(100));
                } else {
                    discount = coupon.getAmount();
                }

                coupon.setUsedCount(coupon.getUsedCount() + 1);
                couponRepository.save(coupon);
            }

            BigDecimal total = base.subtract(discount);

            EnrollmentDetail detail = EnrollmentDetail.builder()
                    .enrollment(enrollment)
                    .shift(shift)
                    .coupon(coupon)
                    .basePrice(base)
                    .appliedDiscount(discount)
                    .total(total)
                    .appliedAt(LocalDateTime.now())
                    .build();

            details.add(detail);
            totalGeneral = totalGeneral.add(total);
        }

        enrollment.setTotalAmount(totalGeneral);
        enrollment = enrollmentRepository.save(enrollment);

        for (EnrollmentDetail detail : details) {
            detail.setEnrollment(enrollment);
        }

        detailRepository.saveAll(details);

        return enrollmentMapper.toResponseDto(enrollment);
    }

    @Override
    public List<EnrollmentResponseDto> getAll() {
        return enrollmentRepository.findAll().stream()
                .map(enrollmentMapper::toResponseDto)
                .toList();
    }
}
