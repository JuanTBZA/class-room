package com.juantirado.virtual_classroom.mapper.academic;

import com.juantirado.virtual_classroom.dto.academic.StudentRequestDto;
import com.juantirado.virtual_classroom.dto.academic.StudentResponseDto;
import com.juantirado.virtual_classroom.entity.academic.Student;
import com.juantirado.virtual_classroom.mapper.auth.UserMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = UserMapper.class)
public interface StudentMapper {

    @Mapping(source = "user", target = "userResponseDto")
    StudentResponseDto toResponseDto(Student student);

    @Mapping(target = "user", ignore = true)
    Student toEntity(StudentRequestDto studentRequestDto);
}