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


    @Override
    @Transactional
    public EnrollmentResponseDto create(EnrollmentRequestDto dto) {
        var enrollment = new Enrollment();

        var student = studentRepository.findById(dto.studentId)
                .orElseThrow(() -> new RuntimeException("Student not found"));

        enrollment.setStudent(student);
        enrollment.setEnrollmentStatus(dto.enrollmentStatus);
        enrollment.setPaymentStatus(dto.paymentStatus);
        enrollment.setPaymentMethod(dto.paymentMethod);
        enrollment.setFileVoucherUrl(dto.fileVoucherUrl);
        enrollment.setEnrollmentDate(LocalDateTime.now());

        var details = dto.details
                .stream()
                .map(detailDto -> {
                    var shift = shiftRepository.findById(detailDto.shiftId)
                            .orElseThrow(() -> new RuntimeException("Shift not found"));

                    var basePrice = shift.getPrice();
                    var discount = BigDecimal.ZERO;
                    Coupon coupon = null;

                    if (detailDto.couponId != null) {
                        coupon = couponRepository.findById(detailDto.couponId)
                                .filter(c -> c.getActive() && c.getValidUntil().isAfter(LocalDateTime.now()))
                                .orElseThrow(() -> new RuntimeException("Invalid or expired coupon"));

                        if (coupon.getUsedCount() >= coupon.getMaxUses()) {
                            throw new RuntimeException("Coupon usage limit reached");
                        }

                        discount = coupon.getType().name().equals("PERCENTAGE")
                                ? basePrice.multiply(coupon.getAmount()).divide(BigDecimal.valueOf(100))
                                : coupon.getAmount();

                        coupon.setUsedCount(coupon.getUsedCount() + 1);
                        couponRepository.save(coupon);
                    }

                    var total = basePrice.subtract(discount);

                    return EnrollmentDetail.builder()
                            .enrollment(enrollment)
                            .shift(shift)
                            .coupon(coupon)
                            .basePrice(basePrice)
                            .appliedDiscount(discount)
                            .total(total)
                            .appliedAt(LocalDateTime.now())
                            .build();
                })
                .toList();

        var totalAmount = details.stream()
                .map(EnrollmentDetail::getTotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        enrollment.setTotalAmount(totalAmount);

        var savedEnrollment = enrollmentRepository.save(enrollment);
        detailRepository.saveAll(details);
        enrollment.setEnrollmentDetails(details);


        return enrollmentMapper.toResponseDto(savedEnrollment);
    }



    @Override
    public List<EnrollmentResponseDto> getAll() {
        return enrollmentRepository.findAll().stream()
                .map(enrollmentMapper::toResponseDto)
                .toList();
    }

    @Override
    public long getTodayEnrollmentCount() {
        return enrollmentRepository.countEnrollmentsToday();
    }
}
