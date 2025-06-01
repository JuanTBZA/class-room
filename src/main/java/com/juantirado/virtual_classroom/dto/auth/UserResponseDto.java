package com.juantirado.virtual_classroom.dto.auth;

import java.util.List;

public record UserResponseDto (
        String name,
        String email,
        String role,
        boolean enabled
){
}
