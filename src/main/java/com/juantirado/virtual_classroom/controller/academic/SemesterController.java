package com.juantirado.virtual_classroom.controller.academic;

import com.juantirado.virtual_classroom.dto.academic.SemesterRequestDto;
import com.juantirado.virtual_classroom.dto.academic.SemesterResponseDto;
import com.juantirado.virtual_classroom.service.academic.SemesterService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/semesters")
@RequiredArgsConstructor
public class SemesterController {

    private final SemesterService semesterService;

    @GetMapping
    public ResponseEntity<List<SemesterResponseDto>> getAll() {
        List<SemesterResponseDto> semesters = semesterService.getAll();
        if (semesters.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(semesterService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<SemesterResponseDto> getById(@PathVariable("id") long id) {
        SemesterResponseDto semester = semesterService.findById(id);
        if (semester == null) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(semester);

    }

    @PostMapping
    public ResponseEntity<SemesterResponseDto> createSemester(@Valid @RequestBody SemesterRequestDto request) {
        SemesterResponseDto semester = semesterService.create(request);
        return new ResponseEntity<>(semester, HttpStatus.CREATED);

    }


    @PutMapping("/{id}")
    public ResponseEntity<SemesterResponseDto> updateSemester(@PathVariable Long id,
                                                          @Valid @RequestBody SemesterRequestDto semesterRequestDto) {
        SemesterResponseDto updated = semesterService.update(id, semesterRequestDto);
        if (updated == null) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(updated);
    }

}
