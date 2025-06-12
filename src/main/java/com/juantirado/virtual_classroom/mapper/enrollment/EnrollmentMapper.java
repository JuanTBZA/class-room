package com.juantirado.virtual_classroom.mapper.enrollment;

import com.juantirado.virtual_classroom.dto.enrollment.EnrollmentResponseDto;
import com.juantirado.virtual_classroom.entity.enrollment.Enrollment;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {EnrollmentDetailMapper.class})
public interface EnrollmentMapper {

    @Mapping(source = "student.id", target = "studentId")
    @Mapping(source = "enrollmentDetails", target = "details")
    EnrollmentResponseDto toResponseDto(Enrollment enrollment);
}
