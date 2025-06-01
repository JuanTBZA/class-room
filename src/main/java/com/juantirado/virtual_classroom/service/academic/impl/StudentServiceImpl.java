package com.juantirado.virtual_classroom.service.academic.impl;

import com.juantirado.virtual_classroom.entity.academic.Student;
import com.juantirado.virtual_classroom.repository.academic.StudentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class StudentServiceImpl {
    private final StudentRepository studentRepository;



    public Student create(Student student) {
        // Busco mi user del student, sino lo creo
        return null;
    }
}
