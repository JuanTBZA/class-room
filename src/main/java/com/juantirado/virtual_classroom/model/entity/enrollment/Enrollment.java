package com.juantirado.virtual_classroom.model.entity.enrollment;

import com.juantirado.virtual_classroom.model.entity.academic.Course;
import com.juantirado.virtual_classroom.model.entity.academic.Student;
import com.juantirado.virtual_classroom.model.enums.EnrollmentStatus;
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
        private EnrollmentStatus status;

        @Column(name = "file_voucher_url")
        private String fileVoucherUrl;

        @Column(name = "enrollment_date")
        private LocalDateTime enrollmentDate;

        @Column(name = "total_amount")
        private BigDecimal totalAmount;




}
