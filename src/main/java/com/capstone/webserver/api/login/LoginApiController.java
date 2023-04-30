package com.capstone.webserver.api.login;

import com.capstone.webserver.dto.TokenInfoDTO;
import com.capstone.webserver.dto.UserDTO;
import com.capstone.webserver.entity.user.User;
import com.capstone.webserver.repository.UserRepository;
import com.capstone.webserver.service.login.LoginService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@Tag(name = "login", description = "로그인 API")
public class LoginApiController {
    @Autowired
    LoginService loginService;

    @Autowired
    UserRepository userRepository;

    /*
     * API Request: 로그인
     * permission: All User
     */
    @Operation(summary = "Login Page",
            description = "로그인 페이지\n\n"
                    + "idUser(학번/교번), pwUser 입력 필요")
    @PostMapping("/api/login/login")
    public ResponseEntity<TokenInfoDTO> login(@RequestBody UserDTO.LoginForm dto) {
        TokenInfoDTO token = loginService.login(dto);

        return ResponseEntity
                .status(token != null ? HttpStatus.OK : HttpStatus.BAD_REQUEST)
                .body(token);
    }
}