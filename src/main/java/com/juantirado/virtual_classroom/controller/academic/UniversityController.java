package com.juantirado.virtual_classroom.controller.academic;

import com.juantirado.virtual_classroom.client.UniversityApiClient;
import com.juantirado.virtual_classroom.client.dto.UniversityResponseDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/universities")
public class UniversityController {

    @Autowired
    private UniversityApiClient universityApiClient;

    @GetMapping("/template")
    public List<UniversityResponseDto> getUniversitiesWithTemplate() {
        return universityApiClient.universities();
    }
    @GetMapping("/client")
    public List<UniversityResponseDto> getUniversitiesWithRestClient() {
        return universityApiClient.getCategoriesWithRestClient();
    }



}
