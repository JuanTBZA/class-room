package com.juantirado.virtual_classroom.service.academic.impl;

import com.juantirado.virtual_classroom.dto.academic.ScheduledCourse.ScheduledCourseRequestDto;
import com.juantirado.virtual_classroom.dto.academic.ScheduledCourse.ScheduledCourseResponseDto;
import com.juantirado.virtual_classroom.entity.academic.ScheduledCourse;
import com.juantirado.virtual_classroom.entity.enums.Day;
import com.juantirado.virtual_classroom.mapper.academic.ScheduledCourseMapper;
import com.juantirado.virtual_classroom.repository.academic.CourseRepository;
import com.juantirado.virtual_classroom.repository.academic.ScheduledCourseRepository;
import com.juantirado.virtual_classroom.repository.academic.ShiftRepository;
import com.juantirado.virtual_classroom.repository.academic.TeacherRepository;
import com.juantirado.virtual_classroom.service.academic.ScheduledCourseService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ScheduledCourseServiceImpl implements ScheduledCourseService {

    private final ScheduledCourseRepository repository;
    private final ScheduledCourseMapper mapper;
    private final CourseRepository courseRepository;
    private final ShiftRepository shiftRepository;
    private final TeacherRepository teacherRepository;

    @Override
    @Transactional
    public ScheduledCourseResponseDto create(ScheduledCourseRequestDto dto) {
        ScheduledCourse entity = mapper.toEntity(dto);

        entity.setCourse(courseRepository.findById(dto.courseId()).orElseThrow());
        entity.setShift(shiftRepository.findById(dto.shiftId()).orElseThrow());
        entity.setTeacher(teacherRepository.findById(dto.teacherId()).orElseThrow());

        entity.setDay(Day.valueOf(dto.day().toUpperCase()));
        entity.setStartTime(LocalTime.parse(dto.startTime()));
        entity.setEndTime(LocalTime.parse(dto.endTime()));

        return mapper.toResponseDto(repository.save(entity));
    }

    @Override
    public List<ScheduledCourseResponseDto> findAll() {
        return repository.findAll().stream()
                .map(mapper::toResponseDto)
                .toList();
    }
}

