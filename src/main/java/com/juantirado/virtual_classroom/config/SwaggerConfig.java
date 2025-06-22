package com.juantirado.virtual_classroom.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.Contact;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Virtual Classroom API")
                        .version("1.0.0")
                        .description("API para la gestión del sistema Virtual Classroom: usuarios, cursos, matrículas, etc.")
                        .contact(new Contact()
                                .name("Juan Tirado")
                                .email("juantiradoboza@gmail.com.com")
                                .url("https://juantiradoboza.online")));
    }
}
