package com.capstone.webserver.config.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

// 패스워드 인코더 객체 생성 클래스
@Configuration
public class BCryptPasswordEncoder {

    @Bean
    public BCryptPasswordEncoder getPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
