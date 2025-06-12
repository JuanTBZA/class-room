package com.juantirado.virtual_classroom.service.auth.impl;

import com.juantirado.virtual_classroom.dto.PaginatedResponseDto;
import com.juantirado.virtual_classroom.dto.auth.UserRequestDto;
import com.juantirado.virtual_classroom.dto.auth.UserResponseDto;
import com.juantirado.virtual_classroom.entity.auth.Role;
import com.juantirado.virtual_classroom.entity.auth.User;
import com.juantirado.virtual_classroom.mapper.auth.UserMapper;
import com.juantirado.virtual_classroom.repository.auth.RoleRepository;
import com.juantirado.virtual_classroom.repository.auth.UserRepository;
import com.juantirado.virtual_classroom.service.auth.UserService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;

    @Override
    public List<UserResponseDto> getAllUsers() {
        return userRepository.findAll().stream().map(userMapper::toResponseDto).toList();
    }

    @Override
    public PaginatedResponseDto<UserResponseDto> getUsersByPage(
            String filtro, int page, int size, String orderBy, String orderDir
    ) {
        Sort sort = orderDir.equalsIgnoreCase("desc")
                ? Sort.by(orderBy).descending()
                : Sort.by(orderBy).ascending();

        Pageable pageable = PageRequest.of(page, size, sort);

        Page<User> pageResult = userRepository.findUsersByFiltro(filtro, pageable);

        List<UserResponseDto> content = pageResult.getContent().stream()
                .map(userMapper::toResponseDto)
                .toList();

        return new PaginatedResponseDto<>(content, pageResult.getTotalElements(), page, size);
    }


    @Override
    public UserResponseDto getById(long id) {
        return userRepository.findById(id).map(userMapper::toResponseDto).orElse(null);
    }

    @Override
    public UserResponseDto delete(long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado con id: " + id));

        user.setEnabled(false);

        return userMapper.toResponseDto(userRepository.save(user));
    }

    @Override
    public User createTeacherUser(UserRequestDto dto) {
        Role teacherRole = roleRepository.findByName("ROLE_TEACHER")
                .orElseThrow(() -> new RuntimeException("Rol TEACHER no encontrado"));

        User user = userMapper.toEntity(dto);
        user.setRole(teacherRole);
        user.setPassword(passwordEncoder.encode(dto.dni()));

        return userRepository.save(user);
    }

    @Override
    public User createStudentUser(UserRequestDto dto) {
        Role studentRole = roleRepository.findByName("ROLE_STUDENT")
                .orElseThrow(() -> new RuntimeException("Rol STUDENT no encontrado"));

        User user = userMapper.toEntity(dto);
        user.setRole(studentRole);
        user.setPassword(passwordEncoder.encode(dto.dni()));

        return userRepository.save(user);
    }






}
