package com.juantirado.virtual_classroom.dto.academic.ScheduledCourse;

import com.juantirado.virtual_classroom.dto.academic.TeacherResponseDto;

import java.time.LocalTime;

public record ScheduledCourseResponseDto(
        Long id,
        TeacherResponseDto teacher,
        CourseBasicResponseDto course,
        String day,
        LocalTime timeStart,
        LocalTime timeEnd

) {
}
