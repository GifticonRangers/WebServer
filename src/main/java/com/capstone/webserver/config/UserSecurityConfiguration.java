package com.capstone.webserver.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class UserSecurityConfiguration {

    @Bean
    public PasswordEncoder getPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf().disable(); // post 방식으로 값을 전송할 때 token을 사용해야 하는 보안 설정 해제

        http
                .authorizeRequests()
                    .antMatchers("/", "/api/signup", "/api/login").permitAll()
                    // 권한에 따라 아래 경로에만 접속가능하게끔 설정
                    .antMatchers("/**").hasAnyRole("ADMIN")
                    .antMatchers("/api/professor/**").hasAnyRole("PROFESSOR")
                    .antMatchers("/api/student/**").hasAnyRole("STUDENT")
                    // 그 외 모든 리소스는 인증이 필요
                    .anyRequest().authenticated();

//        http
//                .formLogin()
//                    .permitAll()
//                    .loginPage("/login"); // Default log-in page

        http
                .logout()
                    .permitAll()
                    .logoutRequestMatcher(new AntPathRequestMatcher("/api/logout")) // Default log-out page, 주소창에 요청해도 POST로 인식
                    .deleteCookies("JSESSIONID") // Delete JESSIONID on log-out
                    .invalidateHttpSession(true) // End Session on log-out
                    .clearAuthentication(true);  // Clear Authentication on log-out

        return http.build();
    }
}