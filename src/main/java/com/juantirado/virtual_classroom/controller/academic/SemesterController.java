package com.juantirado.virtual_classroom.controller.academic;

import com.juantirado.virtual_classroom.dto.academic.CourseRequestDto;
import com.juantirado.virtual_classroom.dto.academic.CourseResponseDto;
import com.juantirado.virtual_classroom.dto.academic.SemesterRequestDto;
import com.juantirado.virtual_classroom.dto.academic.SemesterResponseDto;
import com.juantirado.virtual_classroom.service.academic.SemesterService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/semesters")
@RequiredArgsConstructor
public class SemesterController {

    private final SemesterService semesterService;

    @GetMapping
    public ResponseEntity<List<SemesterResponseDto>> getAll() {
        return ResponseEntity.ok(semesterService.getAll());
    }

    @PutMapping("/{id}")
    public ResponseEntity<SemesterResponseDto> updateSemester(@PathVariable Long id,
                                                          @Valid @RequestBody SemesterRequestDto semesterRequestDto) {
        SemesterResponseDto updated = semesterService.update(id, semesterRequestDto);
        if (updated == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(updated);
    }

}
