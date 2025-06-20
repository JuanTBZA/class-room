package com.juantirado.virtual_classroom.repository.academic;

import com.juantirado.virtual_classroom.entity.academic.Course;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CourseRepository extends JpaRepository<Course, Long> {
    @Query("""
        SELECT c FROM Course c
        WHERE LOWER(c.name) LIKE LOWER(CONCAT('%', :filtro, '%'))
    """)
    Page<Course> findCoursesByName(@Param("filtro") String filtro, Pageable pageable);

}
