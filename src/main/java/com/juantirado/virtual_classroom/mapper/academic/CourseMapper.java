package com.juantirado.virtual_classroom.mapper.academic;

import com.juantirado.virtual_classroom.dto.academic.CourseRequestDto;
import com.juantirado.virtual_classroom.dto.academic.CourseResponseDto;
import com.juantirado.virtual_classroom.entity.academic.Course;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface CourseMapper {


    public CourseResponseDto toResponseDto(Course entity);

    public Course toEntity(CourseRequestDto requestDto);

    @Mapping(target = "id", ignore = true )
    public void updateEntity(CourseRequestDto requestDto, @MappingTarget Course entity);
}
