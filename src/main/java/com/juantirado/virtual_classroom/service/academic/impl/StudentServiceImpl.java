package com.juantirado.virtual_classroom.service.academic.impl;

import com.juantirado.virtual_classroom.dto.academic.StudentRequestDto;
import com.juantirado.virtual_classroom.dto.academic.StudentResponseDto;
import com.juantirado.virtual_classroom.entity.academic.Student;
import com.juantirado.virtual_classroom.entity.auth.User;
import com.juantirado.virtual_classroom.mapper.academic.StudentMapper;
import com.juantirado.virtual_classroom.repository.academic.StudentRepository;
import com.juantirado.virtual_classroom.repository.auth.UserRepository;
import com.juantirado.virtual_classroom.service.academic.StudentService;
import com.juantirado.virtual_classroom.service.auth.UserService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class StudentServiceImpl implements StudentService {

    private final StudentRepository studentRepository;
    private final StudentMapper studentMapper;
    private final UserService userService;
    private final UserRepository userRepository;

    @Override
    public List<StudentResponseDto> getAll() {
        return studentRepository.findAll().stream().map(studentMapper::toResponseDto).toList();
    }

    @Override
    public StudentResponseDto getById(Long id) {
        return studentRepository.findById(id).map(studentMapper::toResponseDto).orElse(null);
    }

    @Transactional
    @Override
    public StudentResponseDto createStudent(StudentRequestDto studentRequestDto) {
        User user = userService.createStudentUser(studentRequestDto.userRequestDto());
        Student student = studentMapper.toEntity(studentRequestDto);
        student.setUser(userRepository.findById(user.getId()).orElseThrow());
        return studentMapper.toResponseDto(studentRepository.save(student));
    }
}
