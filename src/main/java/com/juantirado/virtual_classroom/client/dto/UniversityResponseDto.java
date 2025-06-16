package com.juantirado.virtual_classroom.client.dto;

import java.util.List;

public record UniversityResponseDto (
        String name,
        String country,
        String alpha_two_code,
        String state_province,
        List<String> web_pages,
        List<String> domains
){

}
