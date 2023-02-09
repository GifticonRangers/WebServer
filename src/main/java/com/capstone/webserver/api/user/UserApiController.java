package com.capstone.webserver.api.user;

import com.capstone.webserver.entity.user.User;
import com.capstone.webserver.service.user.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;

@RestController
@Slf4j
public class UserApiController {
    @Autowired
    UserService userService;

    /*
     * API Request: 모든 유저 정보 요청
     * permission: ADMIN
     */
    @GetMapping("/api/admin/show")
    public ResponseEntity<ArrayList<User>> findAllUser() {
        ArrayList<User> user = userService.findAllUser();
        return ResponseEntity
                .status(user != null ? HttpStatus.OK : HttpStatus.BAD_REQUEST)
                .body(user);
    }

    /*
     * API Request: 모든 유저 정보 요청
     * permission: ADMIN
     */
    @GetMapping("/api/admin/show/{type}")
    public ResponseEntity<ArrayList<User>> findAllUserByTypeUser(@PathVariable String type) {
        ArrayList<User> user = userService.findAllUserByTypeUser(type);
        return ResponseEntity
                .status(user != null ? HttpStatus.OK : HttpStatus.BAD_REQUEST)
                .body(user);
    }

    /*
     * API Request: 특정 유저 정보 요청
     * permission: ADMIN
     */
    @GetMapping("/api/admin/show/{id}")
    public ResponseEntity<User> findUserById(@PathVariable Long id) {
        User user = userService.findUserById(id);
        return ResponseEntity
                .status(user != null ? HttpStatus.OK : HttpStatus.BAD_REQUEST)
                .body(user);
    }
}
