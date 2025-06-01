package com.juantirado.virtual_classroom.repository.academic;

import com.juantirado.virtual_classroom.entity.academic.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TeacherRepository extends JpaRepository<Teacher, Long> {
}
