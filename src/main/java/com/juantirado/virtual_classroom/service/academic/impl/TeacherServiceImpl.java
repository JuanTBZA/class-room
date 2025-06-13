package com.juantirado.virtual_classroom.service.academic.impl;

import com.juantirado.virtual_classroom.dto.academic.TeacherRequestDto;
import com.juantirado.virtual_classroom.dto.academic.TeacherResponseDto;
import com.juantirado.virtual_classroom.entity.academic.Teacher;
import com.juantirado.virtual_classroom.entity.auth.User;
import com.juantirado.virtual_classroom.mapper.academic.TeacherMapper;
import com.juantirado.virtual_classroom.repository.academic.TeacherRepository;
import com.juantirado.virtual_classroom.repository.auth.RoleRepository;
import com.juantirado.virtual_classroom.repository.auth.UserRepository;
import com.juantirado.virtual_classroom.service.academic.TeacherService;
import com.juantirado.virtual_classroom.service.auth.UserService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TeacherServiceImpl implements TeacherService {
    private final TeacherRepository teacherRepository;
    private final TeacherMapper teacherMapper;
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;
    private final UserRepository userRepository;

    @Override
    public List<TeacherResponseDto> getAll() {
        return teacherRepository.findAll().stream().map(teacherMapper::toResponseDto).toList();
    }

    @Override
    public TeacherResponseDto getById(Long id) {
        return teacherRepository.findById(id).map(teacherMapper::toResponseDto).orElse(null);
    }


    @Override
    @Transactional
    public TeacherResponseDto createTeacher(TeacherRequestDto teacherRequestDto){
        User user = userService.createTeacherUser(teacherRequestDto.userRequestDto());
        Teacher teacher = teacherMapper.toEntity(teacherRequestDto);
        teacher.setUser(userRepository.findById(user.getId()).orElseThrow());
        return teacherMapper.toResponseDto(teacherRepository.save(teacher));
    }

    @Override
    public long getTotalTeacherCount() {
        return teacherRepository.count();
    }



}
