package com.juantirado.virtual_classroom.mapper.academic;

import com.juantirado.virtual_classroom.dto.academic.ShiftRequestDto;
import com.juantirado.virtual_classroom.dto.academic.ShiftResponseDto;
import com.juantirado.virtual_classroom.entity.academic.Shift;
import com.juantirado.virtual_classroom.entity.enums.ShiftModality;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface ShiftMapper {

    @Mapping(source = "price", target = "price")
    @Mapping(target = "semesterName", source = "semester.name")
    ShiftResponseDto toResponseDto(Shift shift);

    @Mapping(source = "price", target = "price")
    @Mapping(target = "semester", ignore = true)
    Shift toEntity(ShiftRequestDto responseDto);

    @Mapping(target = "semester", ignore = true)
    @Mapping(target = "id", ignore = true)
    void updateEntity(@MappingTarget Shift shift, ShiftRequestDto requestDto);



}
