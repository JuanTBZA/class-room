package com.juantirado.virtual_classroom.mapper.academic;

import com.juantirado.virtual_classroom.dto.academic.SemesterRequestDto;
import com.juantirado.virtual_classroom.dto.academic.SemesterResponseDto;
import com.juantirado.virtual_classroom.entity.academic.Semester;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Mappings;

import java.time.LocalDate;

@Mapper(componentModel = "spring")
public interface SemesterMapper {

    @Mapping(target = "year", expression = "java(String.valueOf(semester.getStartDate().getYear()))")
    @Mapping(target = "state", expression = "java(mapState(semester))")
    SemesterResponseDto toResponseDto(Semester semester);

    Semester toEntity(SemesterRequestDto dto);

    @Mapping(target = "id", ignore = true)
    void updateEntity(SemesterRequestDto dto, @MappingTarget Semester entity);

    default String mapState(Semester semester) {
        LocalDate now = LocalDate.now();
        if (now.isBefore(semester.getStartDate())) return "Pendiente";
        else if (now.isAfter(semester.getEndDate())) return "Finalizado";
        else return "Activo";
    }
}
