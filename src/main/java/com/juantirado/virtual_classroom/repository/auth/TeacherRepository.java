package com.juantirado.virtual_classroom.repository.auth;

import com.juantirado.virtual_classroom.model.entity.auth.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TeacherRepository extends JpaRepository<Teacher, Long> {
}
