package com.juantirado.virtual_classroom.dto.auth;

public record UserRequestDto (
        String name,
        String email,
        String dni,
        Boolean enabled
){
}
