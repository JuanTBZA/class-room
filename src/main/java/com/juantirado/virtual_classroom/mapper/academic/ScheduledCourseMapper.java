package com.juantirado.virtual_classroom.mapper.academic;

import com.juantirado.virtual_classroom.dto.academic.ScheduledCourse.ScheduledCourseRequestDto;
import com.juantirado.virtual_classroom.dto.academic.ScheduledCourse.ScheduledCourseResponseDto;
import com.juantirado.virtual_classroom.entity.academic.ScheduledCourse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ScheduledCourseMapper {

    @Mapping(source = "course.name", target = "courseName")
    @Mapping(source = "shift.name", target = "shiftName")
    @Mapping(source = "teacher.user.name", target = "teacherName")
    ScheduledCourseResponseDto toResponseDto(ScheduledCourse entity);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "course", ignore = true)
    @Mapping(target = "shift", ignore = true)
    @Mapping(target = "teacher", ignore = true)
    ScheduledCourse toEntity(ScheduledCourseRequestDto dto);
}
