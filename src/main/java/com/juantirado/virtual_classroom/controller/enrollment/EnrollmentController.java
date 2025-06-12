package com.juantirado.virtual_classroom.controller.enrollment;

import com.juantirado.virtual_classroom.dto.enrollment.EnrollmentRequestDto;
import com.juantirado.virtual_classroom.dto.enrollment.EnrollmentResponseDto;
import com.juantirado.virtual_classroom.service.enrollment.EnrollmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/enrollments")
@RequiredArgsConstructor
public class EnrollmentController {

    private final EnrollmentService enrollmentService;

    @PostMapping
    public ResponseEntity<EnrollmentResponseDto> create(@RequestBody EnrollmentRequestDto dto) {
        return ResponseEntity.ok(enrollmentService.create(dto));
    }

    @GetMapping
    public ResponseEntity<List<EnrollmentResponseDto>> getAll() {
        List<EnrollmentResponseDto> result = enrollmentService.getAll();
        return result.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(result);
    }
}
