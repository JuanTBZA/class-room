package com.juantirado.virtual_classroom.mapper.security;

import com.juantirado.virtual_classroom.dto.security.AdminRequestDto;
import com.juantirado.virtual_classroom.dto.security.AdminResponseDto;
import com.juantirado.virtual_classroom.entity.security.Admin;
import com.juantirado.virtual_classroom.mapper.auth.UserMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring", uses = UserMapper.class)
public interface AdminMapper {

    @Mapping(source = "user", target = "userResponseDto")
    @Mapping(source = "createdByAdmin.id", target = "createdByAdminId")
    AdminResponseDto toResponseDto(Admin admin);

    @Mapping(target = "user", ignore = true)
    @Mapping(target = "createdByAdmin", ignore = true)
    Admin toEntity(AdminRequestDto dto);

    @Mapping(target = "user", ignore = true)
    @Mapping(target = "createdByAdmin", ignore = true)
    void updateEntityFromDto(AdminRequestDto dto, @MappingTarget Admin entity);
}
