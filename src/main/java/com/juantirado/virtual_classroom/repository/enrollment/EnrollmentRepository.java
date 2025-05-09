package com.juantirado.virtual_classroom.repository.enrollment;

import com.juantirado.virtual_classroom.model.entity.enrollment.Enrollment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EnrollmentRepository extends JpaRepository<Enrollment, Long> {
}
