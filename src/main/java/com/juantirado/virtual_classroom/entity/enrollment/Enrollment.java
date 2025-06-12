package com.juantirado.virtual_classroom.entity.enrollment;

import com.juantirado.virtual_classroom.entity.academic.Student;
import com.juantirado.virtual_classroom.entity.enums.EnrollmentStatus;
import com.juantirado.virtual_classroom.entity.enums.PaymentMethod;
import com.juantirado.virtual_classroom.entity.enums.PaymentStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "coupon")
public class Enrollment {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "student_id")
        @NotNull
        private Student student;

        @Enumerated(EnumType.STRING)
        @Column(name = "enrollment_status")
        private EnrollmentStatus enrollmentStatus;

        @Enumerated(EnumType.STRING)
        @Column(name = "payment_status")
        private PaymentStatus paymentStatus;

        @Enumerated(EnumType.STRING)
        @Column(name = "payment_method")
        private PaymentMethod paymentMethod;

        @Column(name = "file_voucher_url")
        private String fileVoucherUrl;

        @Column(name = "enrollment_date")
        private LocalDateTime enrollmentDate;

        @Column(name = "total_amount")
        private BigDecimal totalAmount;




}
