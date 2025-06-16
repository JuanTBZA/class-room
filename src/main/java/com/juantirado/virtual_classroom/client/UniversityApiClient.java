package com.juantirado.virtual_classroom.client;

import com.juantirado.virtual_classroom.client.dto.UniversityResponseDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;


@Component
public class UniversityApiClient {

    @Autowired
    RestClient restClient;

    @Autowired
    RestTemplate template;

public List<UniversityResponseDto> universities(){
    try {
        var url = "http://universities.hipolabs.com/search?country=Peru";
        ResponseEntity<UniversityResponseDto[]> response = template
                .getForEntity(url, UniversityResponseDto[].class);
        return Arrays.asList(Objects.requireNonNull(response.getBody()));
    } catch (RestClientException e) {
        return List.of();
    }
}


    public List<UniversityResponseDto> getCategoriesWithRestClient() {
        try {

            UniversityResponseDto[] categories = restClient.get()
                    .uri("/search?country=Peru")
                    .retrieve()
                    .body(UniversityResponseDto[].class);
            return Arrays.asList(Objects.requireNonNull(categories));
        } catch (RestClientException e) {
            return List.of();
        }
    }


}
