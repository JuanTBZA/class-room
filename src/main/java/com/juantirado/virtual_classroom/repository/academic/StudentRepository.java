package com.juantirado.virtual_classroom.repository.academic;

import com.juantirado.virtual_classroom.entity.academic.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {
}
