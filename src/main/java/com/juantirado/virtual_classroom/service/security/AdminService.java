package com.juantirado.virtual_classroom.service.security;

import com.juantirado.virtual_classroom.dto.security.AdminRequestDto;
import com.juantirado.virtual_classroom.dto.security.AdminResponseDto;
import jakarta.transaction.Transactional;

import java.util.List;
import java.util.Optional;

public interface AdminService {
    List<AdminResponseDto> getAllAdmins();

    AdminResponseDto getAdminById(Long id);

    Optional<AdminResponseDto> getByUserId(Long userId);

    @Transactional
    AdminResponseDto createAdmin(AdminRequestDto adminRequestDto);

    @Transactional
    AdminResponseDto updateAdmin(Long id, AdminRequestDto adminRequestDto);

    @Transactional
    void deleteAdmin(Long userId);

    long getTotalAdminCount();
}
