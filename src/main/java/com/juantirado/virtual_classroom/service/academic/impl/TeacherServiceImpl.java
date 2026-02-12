package com.juantirado.virtual_classroom.service.academic.impl;

import com.juantirado.virtual_classroom.dto.academic.TeacherRequestDto;
import com.juantirado.virtual_classroom.dto.academic.TeacherResponseDto;
import com.juantirado.virtual_classroom.entity.academic.Teacher;
import com.juantirado.virtual_classroom.entity.auth.User;
import com.juantirado.virtual_classroom.mapper.academic.TeacherMapper;
import com.juantirado.virtual_classroom.common.exception.ResourceNotFoundException;
import com.juantirado.virtual_classroom.repository.academic.TeacherRepository;
import com.juantirado.virtual_classroom.repository.auth.UserRepository;
import com.juantirado.virtual_classroom.security.constants.RoleConstants;
import com.juantirado.virtual_classroom.service.academic.TeacherService;
import com.juantirado.virtual_classroom.service.auth.UserService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TeacherServiceImpl implements TeacherService {
    private final TeacherRepository teacherRepository;
    private final TeacherMapper teacherMapper;
    private final UserService userService;
    private final UserRepository userRepository;

    @Override
    public List<TeacherResponseDto> getAllTeachers() {
        return teacherRepository.findAll().stream().map(teacherMapper::toResponseDto).toList();
    }

    @Override
    public TeacherResponseDto getTeacherById(Long id) {
        return teacherRepository.findById(id)
                .map(teacherMapper::toResponseDto)
                .orElseThrow(() -> new ResourceNotFoundException("El docente con ID " + id + " no existe."));
    }

    @Override
    public Optional<TeacherResponseDto> getByUserId(Long userId) {
        return teacherRepository.findByUserId(userId)
                .map(teacherMapper::toResponseDto);
    }

    @Transactional
    @Override
    public TeacherResponseDto createTeacher(TeacherRequestDto dto) {
        User user = userService.createUserWithRole(
                dto.userRequestDto(),
                RoleConstants.TEACHER
        );

        Teacher teacher = teacherMapper.toEntity(dto);
        teacher.setUser(user);

        return teacherMapper.toResponseDto(teacherRepository.save(teacher));
    }

    @Transactional
    @Override
    public TeacherResponseDto updateTeacher(Long id, TeacherRequestDto dto) {
        Teacher teacher = teacherRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("El docente con ID " + id + " no existe.")
                );

        if (dto.userRequestDto() != null) {
            userService.updateUser(
                    teacher.getUser().getId(),
                    dto.userRequestDto()
            );
        }

        teacherMapper.updateEntityFromDto(dto, teacher);

        return teacherMapper.toResponseDto(
                teacherRepository.save(teacher)
        );
    }

    @Transactional
    @Override
    public void deleteTeacher(Long userId) {
        Teacher teacher = teacherRepository.findByUserId(userId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("El docente con usuario ID " + userId + " no existe.")
                );

        teacherRepository.delete(teacher);
        userService.deleteUser(userId);
    }

    @Override
    public long getTotalTeacherCount() {
        return teacherRepository.count();
    }



}
