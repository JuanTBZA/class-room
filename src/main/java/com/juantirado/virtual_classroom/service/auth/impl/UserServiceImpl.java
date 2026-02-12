package com.juantirado.virtual_classroom.service.auth.impl;

import com.juantirado.virtual_classroom.dto.PaginatedResponseDto;
import com.juantirado.virtual_classroom.dto.auth.UserRequestDto;
import com.juantirado.virtual_classroom.dto.auth.UserResponseDto;
import com.juantirado.virtual_classroom.entity.auth.Role;
import com.juantirado.virtual_classroom.entity.auth.User;
import com.juantirado.virtual_classroom.mapper.auth.UserMapper;
import com.juantirado.virtual_classroom.common.exception.ApiException;
import com.juantirado.virtual_classroom.common.exception.ResourceNotFoundException;
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
import org.springframework.http.HttpStatus;

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
            String filtro, int page, int size, String orderBy, String orderDir, Boolean enabled
    ) {
        Sort sort = orderDir.equalsIgnoreCase("desc")
                ? Sort.by(orderBy).descending()
                : Sort.by(orderBy).ascending();

        Pageable pageable = PageRequest.of(page, size, sort);

        Page<User> pageResult = userRepository.findUsersByFiltroAndEnabled(filtro, enabled, pageable);

        List<UserResponseDto> content = pageResult.getContent().stream()
                .map(userMapper::toResponseDto)
                .toList();

        return new PaginatedResponseDto<>(content, pageResult.getTotalElements(), page, size);
    }

    @Override
    public UserResponseDto getUserById(long id) {
        return userRepository.findById(id)
                .map(userMapper::toResponseDto)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado con id: " + id));
    }




    @Override
    public User createUserWithRole(UserRequestDto dto, String roleName) {
        Role role = roleRepository.findByName(roleName)
                .orElseThrow(() -> new ApiException(
                        HttpStatus.INTERNAL_SERVER_ERROR,
                        "Rol no encontrado: " + roleName
                ));

        User user = userMapper.toEntity(dto);
        user.setRole(role);
        user.setPassword(passwordEncoder.encode(dto.dni()));
        user.setEnabled(true);

        return userRepository.save(user);
    }

    @Override
    public User updateUser(Long userId, UserRequestDto dto) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado"));

        user.setName(dto.name());
        user.setEmail(dto.email());
        user.setDni(dto.dni());
        user.setEnabled(dto.enabled());

        return userRepository.save(user);
    }

    @Override
    public void deleteUser(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado"));

        userRepository.delete(user);
    }







}
