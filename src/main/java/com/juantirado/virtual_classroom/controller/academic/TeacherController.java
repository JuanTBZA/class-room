package com.juantirado.virtual_classroom.controller.academic;

import com.juantirado.virtual_classroom.dto.academic.ShiftResponseDto;
import com.juantirado.virtual_classroom.dto.academic.TeacherRequestDto;
import com.juantirado.virtual_classroom.dto.academic.TeacherResponseDto;
import com.juantirado.virtual_classroom.dto.auth.UserRequestDto;
import com.juantirado.virtual_classroom.dto.auth.UserResponseDto;
import com.juantirado.virtual_classroom.repository.academic.TeacherRepository;
import com.juantirado.virtual_classroom.service.academic.TeacherService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/teachers")
public class TeacherController {

    private final TeacherService teacherService;


    @GetMapping
    public ResponseEntity<List<TeacherResponseDto>> getAllTeachers() {
        List<TeacherResponseDto> teachers = teacherService.getAll();
        if (teachers.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(teachers);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TeacherResponseDto> getTeacherById(@PathVariable Long id) {
        TeacherResponseDto teacherResponseDto = teacherService.getById(id);
        if (teacherResponseDto == null) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(teacherResponseDto);
    }

    @PostMapping
    public ResponseEntity<TeacherResponseDto> createTeacher(@RequestBody TeacherRequestDto teacherRequestDto) {
        TeacherResponseDto createdTeacher = teacherService.createTeacher(teacherRequestDto);
        return ResponseEntity.ok(createdTeacher);
    }
}
