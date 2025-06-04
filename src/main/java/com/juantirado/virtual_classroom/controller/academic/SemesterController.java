package com.juantirado.virtual_classroom.controller.academic;

import com.juantirado.virtual_classroom.dto.academic.SemesterResponseDto;
import com.juantirado.virtual_classroom.service.academic.SemesterService;
import lombok.RequiredArgsConstructor;
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
        return ResponseEntity.ok(semesterService.getAll());
    }


}
