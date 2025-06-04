package com.juantirado.virtual_classroom.dto.auth;

public record UserResponseDto (
        Long id,
        String name,
        String email,
        String role,
        boolean enabled
){
}
