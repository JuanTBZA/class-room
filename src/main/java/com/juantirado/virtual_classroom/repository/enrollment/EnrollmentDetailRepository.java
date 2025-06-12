package com.juantirado.virtual_classroom.repository.enrollment;

import com.juantirado.virtual_classroom.entity.enrollment.EnrollmentDetail;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EnrollmentDetailRepository extends JpaRepository<EnrollmentDetail, Long> {
    List<EnrollmentDetail> findByEnrollmentId(Long enrollmentId);
}
