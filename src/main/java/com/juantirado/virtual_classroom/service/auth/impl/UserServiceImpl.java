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



}
