package com.juantirado.virtual_classroom.service.security.impl;

import com.juantirado.virtual_classroom.dto.auth.UserRequestDto;
import com.juantirado.virtual_classroom.dto.auth.UserResponseDto;
import com.juantirado.virtual_classroom.entity.auth.Role;
import com.juantirado.virtual_classroom.entity.auth.User;
import com.juantirado.virtual_classroom.mapper.auth.UserMapper;
import com.juantirado.virtual_classroom.common.exception.ApiException;
import com.juantirado.virtual_classroom.common.exception.ResourceNotFoundException;
import com.juantirado.virtual_classroom.repository.auth.RoleRepository;
import com.juantirado.virtual_classroom.repository.auth.UserRepository;
import com.juantirado.virtual_classroom.service.security.AdminService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AdminServiceImpl implements AdminService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserResponseDto updateAdmin(long id, UserRequestDto dto) {



        User existing = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado con id: " + id));


        Role adminRole = roleRepository.findByName("ROLE_ADMIN")
                .orElseThrow(() -> new ApiException(HttpStatus.INTERNAL_SERVER_ERROR, "Rol ADMIN no encontrado"));

        // Map DTO to entity, preserve id and adjust fields as needed
        User updated = userMapper.toEntity(dto);
        updated.setId(existing.getId());
        updated.setRole(adminRole);
        updated.setEnabled(existing.getEnabled());

        // Mantener la contraseña existente si no se proporciona dni en el DTO
        if (dto.dni() != null && !dto.dni().isEmpty()) {
            updated.setPassword(passwordEncoder.encode(dto.dni()));
        } else {
            updated.setPassword(existing.getPassword());
        }

        // Si hay campos que deben conservarse del existente (por ejemplo createdAt), el mapper/impl debe ajustarlo.
        User saved = userRepository.save(updated);
        return userMapper.toResponseDto(saved);
    }
}
