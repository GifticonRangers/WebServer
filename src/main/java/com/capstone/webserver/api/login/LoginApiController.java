package com.capstone.webserver.api.login;

import com.capstone.webserver.dto.UserDTO;
import com.capstone.webserver.entity.user.User;
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

    /*
     * API Request: 로그인
     * permission: All User
     */
    @Operation(summary = "login page",
               description = "로그인에 필요한 정보를 입력")
    @PostMapping("/api/login/login")
    public ResponseEntity<User> login(@RequestBody UserDTO.UserForm dto) {
        User user = loginService.login(dto);
        return ResponseEntity
                .status(user != null ? HttpStatus.OK : HttpStatus.BAD_REQUEST)
                .body(user);
    }
}
