package com.juantirado.virtual_classroom.service.academic.impl;

import com.juantirado.virtual_classroom.dto.academic.CourseRequestDto;
import com.juantirado.virtual_classroom.dto.academic.CourseResponseDto;
import com.juantirado.virtual_classroom.mapper.academic.CourseMapper;
import com.juantirado.virtual_classroom.repository.academic.CourseRepository;
import com.juantirado.virtual_classroom.service.academic.CourseService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


@Service
@AllArgsConstructor
public class CourseServiceImpl implements CourseService {
    private final CourseRepository courseRepository;
    private final CourseMapper courseMapper;

    @Override
    public List<CourseResponseDto> getAll() {
        return courseRepository.findByDeletedFalse()
                .stream()
                .map(courseMapper::toResponseDto)
                .toList();
    }

    @Override
    public CourseResponseDto getById(long id) {
        return
                courseRepository
                        .findById(id)
                        .filter(course -> !course.isDeleted())
                        .map(courseMapper::toResponseDto)
                        .orElse(null);
    }

    @Override
    public CourseResponseDto create(CourseRequestDto courseRequestDto) {
        return courseMapper.toResponseDto(
                courseRepository
                .save(courseMapper
                        .toEntity(courseRequestDto)
                )
        );
    }

    @Override
    public CourseResponseDto update(Long id, CourseRequestDto courseRequestDto){
        return courseRepository.findById(id)
                .filter(course -> !course.isDeleted())
                .map(course -> {
                    courseMapper.updateEntity(courseRequestDto,course);
                    return courseRepository.save(course);
                })
                .map(courseMapper::toResponseDto)
                .orElse(null);
    }

    @Override
    public CourseResponseDto delete(Long id)
    {
        return
                courseRepository
                        .findById(id)
                        .filter(course -> !course.isDeleted())
                        .map(course -> {
                            course.setDeleted(true);
                            return courseRepository.save(course);
                        })
                        .map(courseMapper::toResponseDto)
                        .orElse(null);

    }

}
