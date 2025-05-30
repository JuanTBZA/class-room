package com.juantirado.virtual_classroom.repository.enrollment;

import com.juantirado.virtual_classroom.entity.enrollment.EnrollmentDetail;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EnrollmentDetailRepository extends JpaRepository<EnrollmentDetail, Long> {
}
