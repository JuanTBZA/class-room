package com.juantirado.virtual_classroom.service.academic.impl;

import com.juantirado.virtual_classroom.dto.academic.TeacherRequestDto;
import com.juantirado.virtual_classroom.dto.academic.TeacherResponseDto;
import com.juantirado.virtual_classroom.entity.academic.Teacher;
import com.juantirado.virtual_classroom.entity.auth.User;
import com.juantirado.virtual_classroom.mapper.academic.TeacherMapper;
import com.juantirado.virtual_classroom.repository.academic.TeacherRepository;
import com.juantirado.virtual_classroom.repository.auth.UserRepository;
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
    public List<TeacherResponseDto> getAll() {
        return teacherRepository.findAll().stream().map(teacherMapper::toResponseDto).toList();
    }

    @Override
    public TeacherResponseDto getById(Long id) {
        return teacherRepository.findById(id).map(teacherMapper::toResponseDto).orElse(null);
    }

    @Override
    public Optional<TeacherResponseDto> getByUserId(Long userId) {
        return teacherRepository.findByUserId(userId)
                .map(teacherMapper::toResponseDto);
    }





    @Override
    @Transactional
    public TeacherResponseDto createTeacher(TeacherRequestDto teacherRequestDto){
        User user = userService.createTeacherUser(teacherRequestDto.userRequestDto());
        Teacher teacher = teacherMapper.toEntity(teacherRequestDto);
        teacher.setUser(userRepository.findById(user.getId()).orElseThrow(() -> new RuntimeException("El suario no existe.")));
        return teacherMapper.toResponseDto(teacherRepository.save(teacher));
    }

    @Override
    @Transactional
    public TeacherResponseDto updateTeacher(Long id, TeacherRequestDto teacherRequestDto) {
        Teacher existingTeacher = teacherRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("El docente con ID " + id + " no existe."));

        User user = existingTeacher.getUser();
        if (user != null && teacherRequestDto.userRequestDto() != null) {
            user.setName(teacherRequestDto.userRequestDto().name());
            user.setEmail(teacherRequestDto.userRequestDto().email());
            user.setDni(teacherRequestDto.userRequestDto().dni());
            user.setEnabled(teacherRequestDto.userRequestDto().enabled());
            userRepository.save(user);
        }

        teacherMapper.updateEntityFromDto(teacherRequestDto, existingTeacher);

        return teacherMapper.toResponseDto(teacherRepository.save(existingTeacher));
    }



    @Override
    public long getTotalTeacherCount() {
        return teacherRepository.count();
    }

    @Transactional
    @Override
    public void deleteTeacher(Long id) {
        Teacher teacher = teacherRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("El docente con ID " + id + " no existe."));

        User user = teacher.getUser();
        teacherRepository.delete(teacher);
        if (user != null) {
            userRepository.delete(user);
        }
    }




}
