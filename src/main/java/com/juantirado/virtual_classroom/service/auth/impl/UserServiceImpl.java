package com.juantirado.virtual_classroom.service.auth.impl;

import com.juantirado.virtual_classroom.dto.auth.UserRequestDto;
import com.juantirado.virtual_classroom.dto.auth.UserResponseDto;
import com.juantirado.virtual_classroom.entity.auth.User;
import com.juantirado.virtual_classroom.mapper.auth.UserMapper;
import com.juantirado.virtual_classroom.repository.auth.RoleRepository;
import com.juantirado.virtual_classroom.repository.auth.UserRepository;
import com.juantirado.virtual_classroom.service.auth.UserService;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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
    public UserResponseDto getById(long id) {
        return userRepository.findById(id).map(userMapper::toResponseDto).orElse(null);
    }

    @Override
    public UserResponseDto create(UserRequestDto userRequestDto) {
        User user = userMapper.toEntity(userRequestDto);
        user.setPassword(passwordEncoder.encode(userRequestDto.password()));
        user.setRole(roleRepository.findByName(userRequestDto.role()).orElse(null));
        return userMapper.toResponseDto(userRepository.save(user));
    }

    @Override
    public UserResponseDto update(long id, UserRequestDto userRequestDto) {

        User user = userRepository.findById(id)
                .orElseThrow(() ->  new IllegalArgumentException("Usuario no encontrado con id: " + id));

        userRepository.findByEmail(userRequestDto.email())
                .filter(existing -> !existing.getId().equals(id))
                .ifPresent(existing -> {
                    throw new IllegalArgumentException("El correo ya está en uso por otro usuario.");
                });

        userRepository.findByDni(userRequestDto.dni())
                .filter(existing -> !existing.getId().equals(id))
                .ifPresent(existing -> {
                    throw new IllegalArgumentException("El DNI ya está en uso por otro usuario.");
                });

        userMapper.entityToUpdate(userRequestDto, user);

        Optional.ofNullable(userRequestDto.password())
                .filter(p -> !p.isBlank())
                .ifPresent(p -> user.setPassword(passwordEncoder.encode(p)));

        user.setRole(
                roleRepository.findByName(userRequestDto.role())
                        .orElseThrow(() ->  new IllegalArgumentException("Rol no encontrado: " + userRequestDto.role()))
        );

        return userMapper.toResponseDto(userRepository.save(user));


    }





}
