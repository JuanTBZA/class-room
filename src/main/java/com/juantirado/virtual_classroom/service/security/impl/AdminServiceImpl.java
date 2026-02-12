package com.juantirado.virtual_classroom.service.security.impl;

import com.juantirado.virtual_classroom.dto.security.AdminRequestDto;
import com.juantirado.virtual_classroom.dto.security.AdminResponseDto;
import com.juantirado.virtual_classroom.entity.auth.User;
import com.juantirado.virtual_classroom.entity.security.Admin;
import com.juantirado.virtual_classroom.mapper.security.AdminMapper;
import com.juantirado.virtual_classroom.common.exception.ResourceNotFoundException;
import com.juantirado.virtual_classroom.repository.security.AdminRepository;
import com.juantirado.virtual_classroom.security.constants.RoleConstants;
import com.juantirado.virtual_classroom.service.auth.UserService;
import com.juantirado.virtual_classroom.service.security.AdminService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AdminServiceImpl implements AdminService {
    private final AdminRepository adminRepository;
    private final AdminMapper adminMapper;
    private final UserService userService;

    @Override
    public List<AdminResponseDto> getAllAdmins() {
        return adminRepository.findAll().stream().map(adminMapper::toResponseDto).toList();
    }

    @Override
    public AdminResponseDto getAdminById(Long id) {
        return adminRepository.findById(id)
                .map(adminMapper::toResponseDto)
                .orElseThrow(() -> new ResourceNotFoundException("El admin con ID " + id + " no existe."));
    }

    @Override
    public Optional<AdminResponseDto> getByUserId(Long userId) {
        return adminRepository.findByUserId(userId).map(adminMapper::toResponseDto);
    }

    @Transactional
    @Override
    public AdminResponseDto createAdmin(AdminRequestDto dto) {
        User user = userService.createUserWithRole(
                dto.userRequestDto(),
                RoleConstants.ADMIN
        );

        Admin admin = adminMapper.toEntity(dto);
        admin.setUser(user);

        if (dto.createdByAdminId() != null) {
            admin.setCreatedByAdmin(adminRepository.findById(dto.createdByAdminId()).orElse(null));
        }

        if (dto.isMaster() != null) {
            admin.setIsMaster(dto.isMaster());
        }

        return adminMapper.toResponseDto(adminRepository.save(admin));
    }

    @Transactional
    @Override
    public AdminResponseDto updateAdmin(Long id, AdminRequestDto dto) {
        Admin admin = adminRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Admin no existe"));

        if (dto.userRequestDto() != null) {
            userService.updateUser(admin.getUser().getId(), dto.userRequestDto());
        }

        adminMapper.updateEntityFromDto(dto, admin);

        if (dto.createdByAdminId() != null) {
            admin.setCreatedByAdmin(adminRepository.findById(dto.createdByAdminId()).orElse(null));
        }

        if (dto.isMaster() != null) {
            admin.setIsMaster(dto.isMaster());
        }

        return adminMapper.toResponseDto(adminRepository.save(admin));
    }

    @Transactional
    @Override
    public void deleteAdmin(Long userId) {
        Admin admin = adminRepository.findByUserId(userId)
                .orElseThrow(() -> new ResourceNotFoundException("Admin no existe"));

        adminRepository.delete(admin);
        userService.deleteUser(userId);
    }

    @Override
    public long getTotalAdminCount() {
        return adminRepository.count();
    }
}
