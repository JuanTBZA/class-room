package com.juantirado.virtual_classroom.dto;

import java.util.List;

public record PaginatedResponseDto<T>(
        List<T> content,
        long totalElements,
        int page,
        int size
) {
}