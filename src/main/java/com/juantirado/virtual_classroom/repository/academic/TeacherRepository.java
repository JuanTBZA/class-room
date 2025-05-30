package com.juantirado.virtual_classroom.repository.academic;

import com.juantirado.virtual_classroom.entity.auth.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TeacherRepository extends JpaRepository<Teacher, Long> {
}
