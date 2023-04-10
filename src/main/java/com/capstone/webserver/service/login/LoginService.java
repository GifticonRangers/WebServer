package com.capstone.webserver.service.login;

import com.capstone.webserver.config.jwt.JwtTokenProvider;
import com.capstone.webserver.dto.TokenInfoDTO;
import com.capstone.webserver.dto.UserDTO;
import com.capstone.webserver.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class LoginService {
    @Autowired
    UserRepository userRepository;

    private final AuthenticationManagerBuilder authenticationManagerBuilder;

    private final JwtTokenProvider jwtTokenProvider;


    /* Login Service */
    public TokenInfoDTO login(UserDTO.LoginForm dto) {
        // 1. Login ID/PW 를 기반으로 Authentication 객체 생성
        // 이때 authentication 는 인증 여부를 확인하는 authenticated 값이 false
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(dto.getIdUser(), dto.getPwUser());

        // 2. 실제 검증 (사용자 비밀번호 체크)이 이루어지는 부분
        // authenticate 매서드가 실행될 때 CustomUserDetailsService 에서 만든 loadUserByUsername 메서드가 실행
        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
        // 3. 인증 정보를 기반으로 JWT 토큰 생성
        return jwtTokenProvider.generateToken(authentication);



//        User user = dto.toEntity();
//        log.info("User: {}", user.toString());
//        User target = userRepository.findByIdUser(user.getIdUser());
//
//        if (target == null) {
//            log.error("Invalid request: Not found id");
//            return null;
//        }
//
//        if (passwordEncoder.matches(user.getPwUser(), target.getPwUser())) {
//            log.info("Login Success");
//            return target;
//        }
//        else {
//            log.error("Login Failed");
//            return null;
//        }
    }
}
