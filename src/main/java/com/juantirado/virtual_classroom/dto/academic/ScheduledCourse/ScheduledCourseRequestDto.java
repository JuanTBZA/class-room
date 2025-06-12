package com.juantirado.virtual_classroom.dto.academic.ScheduledCourse;

public record ScheduledCourseRequestDto(
        Long courseId,
        Long shiftId,
        Long teacherId,
        String day, // Ej: "MONDAY"
        String startTime, // Ej: "08:00"
        String endTime // Ej: "10:00"
) {}
