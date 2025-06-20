package com.juantirado.virtual_classroom.service.academic.impl;

import com.juantirado.virtual_classroom.dto.PaginatedResponseDto;
import com.juantirado.virtual_classroom.dto.academic.CourseRequestDto;
import com.juantirado.virtual_classroom.dto.academic.CourseResponseDto;
import com.juantirado.virtual_classroom.entity.academic.Course;
import com.juantirado.virtual_classroom.mapper.academic.CourseMapper;
import com.juantirado.virtual_classroom.repository.academic.CourseRepository;
import com.juantirado.virtual_classroom.service.academic.CourseService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CourseServiceImpl implements CourseService {

    private final CourseRepository courseRepository;
    private final CourseMapper courseMapper;

    @Override
    public List<CourseResponseDto> getAll() {
        return courseRepository.findAll()
                .stream()
                .map(courseMapper::toResponseDto)
                .toList();
    }

    @Override
    public CourseResponseDto getById(long id) {
        return courseRepository.findById(id)
                .map(courseMapper::toResponseDto)
                .orElse(null);
    }

    @Override
    public CourseResponseDto create(CourseRequestDto courseRequestDto) {
        Course course = courseMapper.toEntity(courseRequestDto);
        return courseMapper.toResponseDto(courseRepository.save(course));
    }

    @Override
    public CourseResponseDto update(Long id, CourseRequestDto courseRequestDto) {
        return courseRepository.findById(id)
                .map(course -> {
                    courseMapper.updateEntity(courseRequestDto, course);
                    return courseRepository.save(course);
                })
                .map(courseMapper::toResponseDto)
                .orElse(null);
    }

    @Override
    public PaginatedResponseDto<CourseResponseDto> getCoursesByPage(String filtro, int page, int size, String orderBy, String orderDir) {
        Sort sort = orderDir.equalsIgnoreCase("desc")
                ? Sort.by(orderBy).descending()
                : Sort.by(orderBy).ascending();

        Pageable pageable = PageRequest.of(page, size, sort);

        Page<Course> pageResult = courseRepository.findCoursesByName(filtro, pageable);

        List<CourseResponseDto> content = pageResult.getContent().stream()
                .map(courseMapper::toResponseDto)
                .toList();

        return new PaginatedResponseDto<>(content, pageResult.getTotalElements(), page, size);
    }


    @Override
    public CourseResponseDto delete(Long id) {
        return courseRepository.findById(id)
                .map(course -> {
                    courseRepository.deleteById(id);
                    return courseMapper.toResponseDto(course);
                })
                .orElse(null);
    }
}
