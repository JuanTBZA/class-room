package com.juantirado.virtual_classroom.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestTemplate;

@Configuration
public class RestClientConfig {

    @Bean
    public RestClient restClient(RestClient.Builder builder) {
        return builder.baseUrl("http://universities.hipolabs.com").build();
    }

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}