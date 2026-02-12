package com.juantirado.virtual_classroom.controller.academic;

import com.juantirado.virtual_classroom.dto.academic.TeacherRequestDto;
import com.juantirado.virtual_classroom.dto.academic.TeacherResponseDto;
import com.juantirado.virtual_classroom.service.academic.TeacherService;
import lombok.RequiredArgsConstructor;
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
        List<TeacherResponseDto> teachers = teacherService.getAllTeachers();
        if (teachers.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(teachers);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TeacherResponseDto> getTeacherById(@PathVariable Long id) {
        return ResponseEntity.ok(teacherService.getTeacherById(id));
    }

    @PostMapping
    public ResponseEntity<TeacherResponseDto> createTeacher(@RequestBody TeacherRequestDto teacherRequestDto) {
        TeacherResponseDto createdTeacher = teacherService.createTeacher(teacherRequestDto);
        return ResponseEntity.ok(createdTeacher);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TeacherResponseDto> updateTeacher(
            @PathVariable Long id,
            @RequestBody TeacherRequestDto teacherRequestDto) {
        TeacherResponseDto updatedTeacher = teacherService.updateTeacher(id, teacherRequestDto);
        return ResponseEntity.ok(updatedTeacher);
    }


    @GetMapping("/by-user/{userId}")
    public ResponseEntity<TeacherResponseDto> getTeacherByUserId(@PathVariable Long userId) {
        return teacherService.getByUserId(userId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/count")
    public ResponseEntity<Map<String, Long>> getTotalTeacherCount() {
        long count = teacherService.getTotalTeacherCount();
        return ResponseEntity.ok(Map.of("teacherCount", count));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTeacher(@PathVariable Long id) {
        teacherService.deleteTeacher(id);
        return ResponseEntity.ok().build();
    }
}
