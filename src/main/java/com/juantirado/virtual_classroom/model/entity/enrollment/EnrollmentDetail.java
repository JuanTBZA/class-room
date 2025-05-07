package com.juantirado.virtual_classroom.model.entity.enrollment;

import com.juantirado.virtual_classroom.model.entity.academic.Shift;
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
@Table(name = "enrollment_detail")
public class EnrollmentDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "enrollment_id")
    @NotNull
    private Enrollment enrollment;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "coupon_id")
    private Coupon coupon;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "shift_id")
    private Shift shift;

    @Column(name = "base_price")
    private BigDecimal basePrice;

    @Column(name = "applied_discount")
    private BigDecimal appliedDiscount;

    private BigDecimal total;

    private LocalDateTime appliedAt;


}
