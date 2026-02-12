package com.juantirado.virtual_classroom.controller.security;

import com.juantirado.virtual_classroom.dto.security.AdminRequestDto;
import com.juantirado.virtual_classroom.dto.security.AdminResponseDto;
import com.juantirado.virtual_classroom.service.security.AdminService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/admins")
@AllArgsConstructor
public class AdminController {
    private final AdminService adminService;

    @GetMapping
    public ResponseEntity<List<AdminResponseDto>> getAllAdmins() {
        List<AdminResponseDto> admins = adminService.getAllAdmins();
        if (admins.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(admins);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AdminResponseDto> getAdminById(@PathVariable Long id) {
        return ResponseEntity.ok(adminService.getAdminById(id));
    }

    @PostMapping
    public ResponseEntity<AdminResponseDto> createAdmin(@RequestBody AdminRequestDto adminRequestDto) {
        AdminResponseDto created = adminService.createAdmin(adminRequestDto);
        return ResponseEntity.ok(created);
    }

    @PutMapping("/{id}")
    public ResponseEntity<AdminResponseDto> updateAdmin(@PathVariable Long id, @RequestBody AdminRequestDto adminRequestDto) {
        AdminResponseDto updated = adminService.updateAdmin(id, adminRequestDto);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAdmin(@PathVariable Long id) {
        adminService.deleteAdmin(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/by-user/{userId}")
    public ResponseEntity<AdminResponseDto> getAdminByUserId(@PathVariable Long userId) {
        return adminService.getByUserId(userId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/count")
    public ResponseEntity<Map<String, Long>> getAdminCount() {
        long count = adminService.getTotalAdminCount();
        return ResponseEntity.ok(Map.of("adminCount", count));
    }

}
