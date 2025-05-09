package com.juantirado.virtual_classroom.repository.auth;

import com.juantirado.virtual_classroom.model.entity.auth.Student;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentRepository extends JpaRepository<Student, Long> {
}
