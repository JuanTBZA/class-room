package com.juantirado.virtual_classroom.mapper.academic;

import com.juantirado.virtual_classroom.dto.academic.TeacherRequestDto;
import com.juantirado.virtual_classroom.dto.academic.TeacherResponseDto;
import com.juantirado.virtual_classroom.entity.academic.Teacher;
import com.juantirado.virtual_classroom.mapper.auth.UserMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = UserMapper.class)
public interface TeacherMapper {

    @Mapping(source = "user", target = "userResponseDto")
    TeacherResponseDto toResponseDto(Teacher teacher);

    @Mapping(target = "user", ignore = true)
    Teacher  toEntity(TeacherRequestDto teacherRequestDto);


}
