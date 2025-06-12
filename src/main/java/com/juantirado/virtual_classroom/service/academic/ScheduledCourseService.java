package com.juantirado.virtual_classroom.service.academic;

import com.juantirado.virtual_classroom.dto.academic.ScheduledCourse.ScheduledCourseRequestDto;
import com.juantirado.virtual_classroom.dto.academic.ScheduledCourse.ScheduledCourseResponseDto;

import java.util.List;

public interface ScheduledCourseService {
    ScheduledCourseResponseDto create(ScheduledCourseRequestDto dto);
    List<ScheduledCourseResponseDto> findAll();
}
