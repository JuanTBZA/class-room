package com.juantirado.virtual_classroom.controller.enrollment;

import com.juantirado.virtual_classroom.dto.enrollment.EnrollmentDetailResponseDto;
import com.juantirado.virtual_classroom.service.enrollment.EnrollmentDetailService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/enrollment-details")
@RequiredArgsConstructor
public class EnrollmentDetailController {

    private final EnrollmentDetailService detailService;

    @GetMapping("/{enrollmentId}")
    public ResponseEntity<List<EnrollmentDetailResponseDto>> getByEnrollmentId(@PathVariable Long enrollmentId) {
        List<EnrollmentDetailResponseDto> result = detailService.getByEnrollmentId(enrollmentId);
        return result.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(result);
    }
}
