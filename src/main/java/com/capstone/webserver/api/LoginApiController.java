package com.capstone.webserver.api;

import com.capstone.webserver.dto.UserForm;
import com.capstone.webserver.entity.User;
import com.capstone.webserver.service.LoginService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class LoginApiController {
    @Autowired
    LoginService loginService;

    /*
     * API Request: 로그인
     * permission: All User
     */
    @PostMapping("/api/login")
    public ResponseEntity<User> login(@RequestBody UserForm dto) {
        User user = loginService.login(dto);
        return ResponseEntity
                .status(user != null ? HttpStatus.OK : HttpStatus.BAD_REQUEST)
                .body(user);
    }
}
