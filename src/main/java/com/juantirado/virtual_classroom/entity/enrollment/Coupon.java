package com.juantirado.virtual_classroom.entity.enrollment;

import com.juantirado.virtual_classroom.entity.enums.CouponType;
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
public class Coupon {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(length = 50, unique = true)
    private String code;

    private String description;

    @NotNull
    @Column(name = "coupon_type")
    @Enumerated(EnumType.STRING)
    private CouponType type;

    @NotNull
    private BigDecimal amount;

    @Column(name = "max_uses")
    private int maxUses;

    @Column(name = "used_count")
    private int usedCount;

    @Column(name = "valid_from")
    private LocalDateTime validFrom;

    @Column(name = "valid_until")
    private LocalDateTime validUntil;

    @Column(name = "is_active",columnDefinition = "BOOLEAN DEFAULT TRUE")
    private Boolean active;

    @PrePersist
    void setPersist(){
        active = true;
        usedCount = 0;
        validFrom = LocalDateTime.now();
    }
}
