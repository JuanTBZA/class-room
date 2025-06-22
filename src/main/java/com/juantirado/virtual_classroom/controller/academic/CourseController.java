package com.juantirado.virtual_classroom.controller.academic;

import com.juantirado.virtual_classroom.dto.PaginatedResponseDto;
import com.juantirado.virtual_classroom.dto.academic.CourseRequestDto;
import com.juantirado.virtual_classroom.dto.academic.CourseResponseDto;
import com.juantirado.virtual_classroom.service.academic.CourseService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/courses")
@Tag(name = "Cursos", description = "API REST para gestionar cursos del sistema educativo")
@RequiredArgsConstructor
public class CourseController {

    private final CourseService courseService;

    @Operation(
            summary = "Listar todos los cursos",
            description = "Devuelve todos los cursos registrados",
            operationId = "getAllCourses",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Lista de cursos",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = CourseResponseDto.class))
                    ),
                    @ApiResponse(responseCode = "204", description = "No hay cursos registrados"),
                    @ApiResponse(responseCode = "500", description = "Error del servidor")
            }
    )
    @GetMapping
    public ResponseEntity<List<CourseResponseDto>> getAllCourses() {
        try {
            List<CourseResponseDto> courses = courseService.getAll();
            if (courses.isEmpty()) return ResponseEntity.noContent().build();
            return ResponseEntity.ok(courses);
        } catch (Exception e) {
            log.error("Error al listar cursos", e);
            return ResponseEntity.internalServerError().build();
        }
    }

    @Operation(
            summary = "Obtener curso por ID",
            description = "Devuelve los datos de un curso según su ID",
            operationId = "getCourseById"
    )
    @GetMapping("/{id}")
    public ResponseEntity<CourseResponseDto> getCourseById(@PathVariable Long id) {
        try {
            CourseResponseDto course = courseService.getById(id);
            if (course == null) return ResponseEntity.noContent().build();
            return ResponseEntity.ok(course);
        } catch (Exception e) {
            log.error("Error al obtener curso por ID", e);
            return ResponseEntity.internalServerError().build();
        }
    }

    @Operation(
            summary = "Crear nuevo curso",
            description = "Registra un nuevo curso en el sistema",
            operationId = "createCourse"
    )
    @PostMapping
    public ResponseEntity<CourseResponseDto> createCourse(@Valid @RequestBody CourseRequestDto dto) {
        try {
            CourseResponseDto created = courseService.create(dto);
            return new ResponseEntity<>(created, HttpStatus.CREATED);
        } catch (Exception e) {
            log.error("Error al crear curso", e);
            return ResponseEntity.internalServerError().build();
        }
    }

    @Operation(
            summary = "Actualizar curso existente",
            description = "Modifica un curso por ID",
            operationId = "updateCourse"
    )
    @PutMapping("/{id}")
    public ResponseEntity<CourseResponseDto> updateCourse(@PathVariable Long id,
                                                          @Valid @RequestBody CourseRequestDto dto) {
        try {
            CourseResponseDto updated = courseService.update(id, dto);
            if (updated == null) return ResponseEntity.noContent().build();
            return ResponseEntity.ok(updated);
        } catch (Exception e) {
            log.error("Error al actualizar curso", e);
            return ResponseEntity.internalServerError().build();
        }
    }

    @Operation(
            summary = "Eliminar curso por ID",
            description = "Elimina un curso dado su ID",
            operationId = "deleteCourse"
    )
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCourse(@PathVariable Long id) {
        try {
            courseService.delete(id);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            log.error("Error al eliminar curso", e);
            return ResponseEntity.internalServerError().build();
        }
    }

    @Operation(
            summary = "Listar cursos paginados",
            description = "Permite filtrar, paginar y ordenar la lista de cursos",
            operationId = "getCoursesPaginated"
    )
    @GetMapping("/paginated")
    public ResponseEntity<PaginatedResponseDto<CourseResponseDto>> getCoursesPaginated(
            @RequestParam(defaultValue = "") String filtro,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "name") String orderBy,
            @RequestParam(defaultValue = "asc") String orderDir
    ) {
        try {
            var result = courseService.getCoursesByPage(filtro, page, size, orderBy, orderDir);
            if (result.content().isEmpty()) return ResponseEntity.noContent().build();
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            log.error("Error al paginar cursos", e);
            return ResponseEntity.internalServerError().build();
        }
    }
}
