package com.juantirado.virtual_classroom.controller.academic;

import com.juantirado.virtual_classroom.dto.academic.TeacherRequestDto;
import com.juantirado.virtual_classroom.dto.academic.TeacherResponseDto;
import com.juantirado.virtual_classroom.service.academic.TeacherService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/teachers")
public class TeacherController {

    private final TeacherService teacherService;

    @GetMapping
    public ResponseEntity<List<TeacherResponseDto>> getAllTeachers() {
        try {
            List<TeacherResponseDto> teachers = teacherService.getAll();
            if (teachers.isEmpty()) {
                return ResponseEntity.noContent().build();
            }
            return ResponseEntity.ok(teachers);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<TeacherResponseDto> getTeacherById(@PathVariable Long id) {
        try {
            TeacherResponseDto teacherResponseDto = teacherService.getById(id);
            if (teacherResponseDto == null) {
                return ResponseEntity.notFound().build();
            }
            return ResponseEntity.ok(teacherResponseDto);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @PostMapping
    public ResponseEntity<TeacherResponseDto> createTeacher(@RequestBody TeacherRequestDto teacherRequestDto) {
        try {
            TeacherResponseDto createdTeacher = teacherService.createTeacher(teacherRequestDto);
            return ResponseEntity.ok(createdTeacher);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @GetMapping("/count")
    public ResponseEntity<Map<String, Long>> getTotalTeacherCount() {
        try {
            long count = teacherService.getTotalTeacherCount();
            return ResponseEntity.ok(Map.of("teacherCount", count));
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTeacher(@PathVariable Long id) {
        try {
            teacherService.deleteTeacher(id);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }
}
