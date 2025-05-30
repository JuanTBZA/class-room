package com.juantirado.virtual_classroom.repository.academic;

import com.juantirado.virtual_classroom.entity.auth.Student;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentRepository extends JpaRepository<Student, Long> {
}
