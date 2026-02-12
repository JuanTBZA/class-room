package com.juantirado.virtual_classroom.service.academic.impl;

import com.juantirado.virtual_classroom.dto.academic.StudentRequestDto;
import com.juantirado.virtual_classroom.dto.academic.StudentResponseDto;
import com.juantirado.virtual_classroom.entity.academic.Student;
import com.juantirado.virtual_classroom.entity.auth.User;
import com.juantirado.virtual_classroom.mapper.academic.StudentMapper;
import com.juantirado.virtual_classroom.common.exception.ResourceNotFoundException;
import com.juantirado.virtual_classroom.repository.academic.StudentRepository;
import com.juantirado.virtual_classroom.repository.auth.UserRepository;
import com.juantirado.virtual_classroom.security.constants.RoleConstants;
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

    @Override
    public List<StudentResponseDto> getAllStudents() {
        return studentRepository.findAll().stream().map(studentMapper::toResponseDto).toList();
    }

    @Override
    public StudentResponseDto getStudentById(Long id) {
        return studentRepository.findById(id)
                .map(studentMapper::toResponseDto)
                .orElseThrow(() -> new ResourceNotFoundException("El estudiante con ID " + id + " no existe."));
    }

    @Transactional
    @Override
    public StudentResponseDto createStudent(StudentRequestDto dto) {
        User user = userService.createUserWithRole(
                dto.userRequestDto(),
                RoleConstants.STUDENT
        );

        Student student = studentMapper.toEntity(dto);
        student.setUser(user);

        return studentMapper.toResponseDto(studentRepository.save(student));
    }


    @Transactional
    @Override
    public StudentResponseDto updateStudent(Long id, StudentRequestDto dto) {
        Student student = studentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Estudiante no existe"));

        if (dto.userRequestDto() != null) {
            userService.updateUser(
                    student.getUser().getId(),
                    dto.userRequestDto()
            );
        }

        studentMapper.updateEntityFromDto(dto, student);
        return studentMapper.toResponseDto(studentRepository.save(student));
    }


    @Override
    public StudentResponseDto getByUserId(Long id) {
        return studentRepository.findByUserId(id)
                .map(studentMapper::toResponseDto)
                .orElseThrow(() -> new ResourceNotFoundException("El estudiante con usuario ID " + id + " no existe."));
    }


    @Transactional
    @Override
    public void deleteStudent(Long userId) {
        Student student = studentRepository.findByUserId(userId)
                .orElseThrow(() -> new ResourceNotFoundException("Estudiante no existe"));

        studentRepository.delete(student);
        userService.deleteUser(userId);
    }




    @Override
    public long getActiveStudentCount() {
        return studentRepository.countActiveStudents();
    }
}
