package com.capstone.webserver.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
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
    public GroupedOpenApi tokenApi() {
        return GroupedOpenApi.builder()
                .group("token-api")
                .pathsToMatch("/api/token/**")
                .build();
    }

    @Bean
    public GroupedOpenApi nfcApi() {
        return GroupedOpenApi.builder()
                .group("nfc-api")
                .pathsToMatch("/api/nfc/**")
                .build();
    }

    @Bean
    public OpenAPI openAPI() {

        Info info = new Info()
                .title("PBBS Attendance System Example")
                .description("INU Capstone-designs, PBBS Attendance System Example")
                .version("v0.0.1");

        // SecuritySecheme명
        String jwtSchemeName = "jwtAuth";
        // API 요청헤더에 인증정보 포함
        SecurityRequirement securityRequirement = new SecurityRequirement().addList(jwtSchemeName);
        // SecuritySchemes 등록
        Components components = new Components()
                .addSecuritySchemes(jwtSchemeName, new SecurityScheme()
                        .name(jwtSchemeName)
                        .type(SecurityScheme.Type.HTTP) // HTTP 방식
                        .scheme("bearer")
                        .bearerFormat("JWT")); // 토큰 형식을 지정하는 임의의 문자(Optional)

        return new OpenAPI()
                .info(info)
                .addSecurityItem(securityRequirement)
                .components(components);
    }
}