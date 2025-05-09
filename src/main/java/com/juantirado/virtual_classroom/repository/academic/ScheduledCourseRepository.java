package com.juantirado.virtual_classroom.repository.academic;

import com.juantirado.virtual_classroom.model.entity.academic.ScheduledCourse;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ScheduledCourseRepository extends JpaRepository<ScheduledCourse, Long> {
}
