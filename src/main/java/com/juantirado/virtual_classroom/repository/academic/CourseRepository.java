package com.juantirado.virtual_classroom.repository.academic;

import com.juantirado.virtual_classroom.entity.academic.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

@Repository
public interface CourseRepository extends JpaRepository<Course, Long> {
    List<Course> findByDeletedFalse();

}
