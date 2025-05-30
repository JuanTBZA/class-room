package com.juantirado.virtual_classroom.repository.enrollment;

import com.juantirado.virtual_classroom.entity.enrollment.Coupon;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CouponRepository extends JpaRepository<Coupon, Long> {
}
