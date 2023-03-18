package com.capstone.webserver.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springdoc.core.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {
    @Bean
    public GroupedOpenApi attendanceApi() {
        return GroupedOpenApi.builder()
                .group("attendance-api")
                .pathsToMatch("/api/attendance/**")
                .build();
    }

    @Bean
    public GroupedOpenApi loginApi() {
        return GroupedOpenApi.builder()
                .group("login-api")
                .pathsToMatch("/api/login/**")
                .build();
    }

    @Bean
    public GroupedOpenApi subjectApi() {
        return GroupedOpenApi.builder()
                .group("subject-api")
                .pathsToMatch("/api/subject/**")
                .build();
    }

    @Bean
    public GroupedOpenApi userApi() {
        return GroupedOpenApi.builder()
                .group("user-api")
                .pathsToMatch("/api/user/**")
                .build();
    }

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .components(new Components())
                .info(new Info().title("Spring Boot API Example")
                        .description("Spring Boot API 예시 프로젝트입니다.")
                        .version("v0.0.1"));
    }
}