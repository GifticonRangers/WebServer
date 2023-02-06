package com.capstone.webserver.api;

import com.capstone.webserver.dto.UserForm;
import com.capstone.webserver.entity.User;
import com.capstone.webserver.service.SignUpService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


@RestController
@Slf4j
public class SignUpApiController {
    @Autowired
    SignUpService signUpService;

    /*
     * API Request: 회원가입
     * permission: All User
     */
    @PostMapping("/api/signup")
    public ResponseEntity<User> signup(@RequestBody UserForm dto) {
        User user = signUpService.signup(dto);

        return ResponseEntity
                .status(user != null ? HttpStatus.OK : HttpStatus.BAD_REQUEST)
                .body(user);
    }
}
