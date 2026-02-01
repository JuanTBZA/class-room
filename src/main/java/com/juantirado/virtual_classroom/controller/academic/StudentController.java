package com.juantirado.virtual_classroom.controller.academic;

import com.juantirado.virtual_classroom.dto.academic.StudentRequestDto;
import com.juantirado.virtual_classroom.dto.academic.StudentResponseDto;
import com.juantirado.virtual_classroom.service.academic.StudentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/students")
public class StudentController {

    private final StudentService studentService;

    @GetMapping
    public ResponseEntity<List<StudentResponseDto>> getAllStudents() {
        List<StudentResponseDto> students = studentService.getAll();
        if (students.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(students);
    }

    @GetMapping("/{id}")
    public ResponseEntity<StudentResponseDto> getStudentById(@PathVariable Long id) {
        return ResponseEntity.ok(studentService.getById(id));
    }

    @PostMapping
    public ResponseEntity<StudentResponseDto> createStudent(@RequestBody StudentRequestDto studentRequestDto) {
        StudentResponseDto created = studentService.createStudent(studentRequestDto);
        return ResponseEntity.ok(created);
    }

    @PutMapping("/{id}")
    public ResponseEntity<StudentResponseDto> updateStudent(@PathVariable Long id, @RequestBody StudentRequestDto studentRequestDto) {
        StudentResponseDto updated = studentService.updateStudent(id, studentRequestDto);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteStudent(@PathVariable Long id) {
        studentService.deleteStudent(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/by-user/{userId}")
    public ResponseEntity<StudentResponseDto> getStudentByUserId(@PathVariable Long userId) {
        return ResponseEntity.ok(studentService.getByUserId(userId));
    }


    @GetMapping("/count")
    public ResponseEntity<Map<String, Long>> getStudentCount() {
        long count = studentService.getActiveStudentCount();
        return ResponseEntity.ok(Map.of("studentCount", count));
    }
}
