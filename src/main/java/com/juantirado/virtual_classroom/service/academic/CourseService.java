package com.juantirado.virtual_classroom.service.academic;

import com.juantirado.virtual_classroom.dto.academic.CourseRequestDto;
import com.juantirado.virtual_classroom.dto.academic.CourseResponseDto;

import java.util.List;

public interface CourseService {
    List<CourseResponseDto> getAll();

    CourseResponseDto getById(long id);

    CourseResponseDto create(CourseRequestDto courseRequestDto);

    CourseResponseDto update(Long id, CourseRequestDto courseRequestDto);

    CourseResponseDto delete(Long id);
}
