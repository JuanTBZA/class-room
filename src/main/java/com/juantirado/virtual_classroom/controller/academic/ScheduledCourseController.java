package com.juantirado.virtual_classroom.controller.academic;

import com.juantirado.virtual_classroom.dto.academic.ScheduledCourse.ScheduledCourseRequestDto;
import com.juantirado.virtual_classroom.dto.academic.ScheduledCourse.ScheduledCourseResponseDto;
import com.juantirado.virtual_classroom.service.academic.ScheduledCourseService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/schedules")
@RequiredArgsConstructor
public class ScheduledCourseController {

    private final ScheduledCourseService service;

    @PostMapping
    public ResponseEntity<ScheduledCourseResponseDto> create(@RequestBody ScheduledCourseRequestDto dto) {
        return ResponseEntity.ok(service.create(dto));
    }

    @GetMapping
    public ResponseEntity<List<ScheduledCourseResponseDto>> getAll() {
        List<ScheduledCourseResponseDto> result = service.findAll();
        return result.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(result);
    }
}
