package com.juantirado.virtual_classroom.dto.academic.ScheduledCourse;

public record ScheduledCourseResponseDto(
        Long id,
        String courseName,
        String shiftName,
        String teacherName,
        String day,
        String startTime,
        String endTime
) {}
