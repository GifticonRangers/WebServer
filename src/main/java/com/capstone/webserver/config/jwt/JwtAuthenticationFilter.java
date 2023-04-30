package com.capstone.webserver.config.jwt;

import com.capstone.webserver.config.error.CustomException;
import com.capstone.webserver.dto.TokenInfoDTO;
import com.capstone.webserver.repository.UserRepository;
import com.capstone.webserver.service.user.UserService;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.SecurityException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends GenericFilterBean {

    private final JwtTokenProvider jwtTokenProvider;

    @Autowired
    UserRepository userRepository;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

        try {        // 1. Request Header 에서 JWT 토큰 추출
            String token = resolveToken((HttpServletRequest) request);

            // 2. validateToken 으로 토큰 유효성 검사

            if (token != null && jwtTokenProvider.validateToken(token)) {
                // 토큰이 유효할 경우 토큰에서 Authentication 객체를 가지고 와서 SecurityContext 에 저장
                Authentication authentication = jwtTokenProvider.getAuthentication(token);
                SecurityContextHolder.getContext().setAuthentication(authentication);

                // refreshToken 갱신
                TokenInfoDTO tokenInfoDTO = jwtTokenProvider.generateToken(authentication);
                HttpServletResponse httpServletResponse = (HttpServletResponse) response;
                httpServletResponse.setHeader("Authorization", "Bearer " + tokenInfoDTO.getAccessToken());
                if (tokenInfoDTO.getRefreshToken() != null) {
                    Cookie refreshTokenCookie = new Cookie("refreshToken", tokenInfoDTO.getRefreshToken());
                    refreshTokenCookie.setMaxAge(30 * 24 * 60 * 60);
                    refreshTokenCookie.setPath("/");
                    httpServletResponse.addCookie(refreshTokenCookie);
                }
            }
        } catch (CustomException e) {
            request.setAttribute("exception", e.getErrorCode());
        }
        chain.doFilter(request, response);
    }

    // Request Header 에서 토큰 정보 추출
    private String resolveToken(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (bearerToken != null) {
            bearerToken = bearerToken.trim();
            if (bearerToken.startsWith("Bearer ")) {
                return bearerToken.substring(7).trim();
            }
        }
        return null;
    }
}