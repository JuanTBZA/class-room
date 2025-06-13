package com.juantirado.virtual_classroom.controller.enrollment;

import com.juantirado.virtual_classroom.dto.enrollment.EnrollmentRequestDto;
import com.juantirado.virtual_classroom.dto.enrollment.EnrollmentResponseDto;
import com.juantirado.virtual_classroom.service.enrollment.EnrollmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

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

        @GetMapping("/today-count")
    public ResponseEntity<Map<String, Long>> getTodayEnrollmentCount() {
        long count = enrollmentService.getTodayEnrollmentCount();
        return ResponseEntity.ok(Map.of("enrollmentsToday", count));
    }
}
