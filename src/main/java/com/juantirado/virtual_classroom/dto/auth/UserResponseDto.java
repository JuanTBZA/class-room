package com.juantirado.virtual_classroom.dto.auth;

import java.util.List;

public record UserResponseDto (
        Long id,
        String name,
        String email,
        String role,
        boolean enabled
){
}
