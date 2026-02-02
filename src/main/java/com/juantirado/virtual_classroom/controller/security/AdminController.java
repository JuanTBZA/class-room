package com.juantirado.virtual_classroom.controller.security;

import com.juantirado.virtual_classroom.dto.auth.UserRequestDto;
import com.juantirado.virtual_classroom.dto.auth.UserResponseDto;
import com.juantirado.virtual_classroom.service.security.AdminService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admins")
@AllArgsConstructor
public class AdminController {
    private final AdminService adminService;

    @PutMapping("/{id}")
    public ResponseEntity<UserResponseDto> updateAdmin(@PathVariable long id, @RequestBody UserRequestDto dto) {
        UserResponseDto response = adminService.updateAdmin(id, dto);
        return ResponseEntity.ok(response);
    }
}
