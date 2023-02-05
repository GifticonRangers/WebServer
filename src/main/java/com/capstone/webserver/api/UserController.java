package com.capstone.webserver.api;

import com.capstone.webserver.entity.Role;
import com.capstone.webserver.entity.User;
import com.capstone.webserver.service.UserService;
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
public class UserController {
    @Autowired
    UserService userService;

    /* api 요청: 모든 유저 정보 요청 */
    @GetMapping("/api/show")
    public ResponseEntity<ArrayList<User>> showAllUser() {
        ArrayList<User> user = userService.showAllUser();
        return ResponseEntity.status(user != null ? HttpStatus.OK : HttpStatus.BAD_REQUEST).body(user);
    }

    /* api 요청: 타입별 유저 정보 요청 */
    @GetMapping("/api/show/type/{type}")
    public ResponseEntity<ArrayList<User>> showTypeUser(@PathVariable int type) {
        ArrayList<User> user = userService.showTypeUser(Role.values()[type]);
        return ResponseEntity.status(user != null ? HttpStatus.OK : HttpStatus.BAD_REQUEST).body(user);
    }

    /* api 요청: id에 따른 유저 정보 요청 */
    @GetMapping("/api/show/{id}")
    public ResponseEntity<User> showUser(@PathVariable Long id) {
        User user = userService.showUser(id);
        return ResponseEntity.status(user != null ? HttpStatus.OK : HttpStatus.BAD_REQUEST).body(user);
    }
}
