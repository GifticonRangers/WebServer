package com.capstone.webserver.api.user;

import com.capstone.webserver.entity.user.User;
import com.capstone.webserver.service.user.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "user", description = "유저 정보 요청 API")
public class UserApiController {
    @Autowired
    UserService userService;

    /*
     * API Request: 모든 유저 정보 요청
     * permission: ADMIN
     */
    @Operation(summary = "all-user info",
               description = "전체 유저 정보를 요청")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                         description = "successful operation",
                         content = @Content(schema = @Schema(implementation = User.class))),
            @ApiResponse(responseCode = "400",
                         description = "bad request operation")
    })
    @GetMapping("/api/user/show")
    public ResponseEntity<ArrayList<User>> findAllUser() {
        ArrayList<User> user = userService.findAllUser();
        return ResponseEntity
                .status(user != null ? HttpStatus.OK : HttpStatus.BAD_REQUEST)
                .body(user);
    }

    /*
     * API Request: 타입별 모든 유저 정보 요청
     * permission: ADMIN
     */
    @Operation(summary = "all-user info by type",
               description = "타입별 전체 유저 정보를 요청")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                         description = "successful operation",
                         content = @Content(schema = @Schema(implementation = User.class))),
            @ApiResponse(responseCode = "400",
                         description = "bad request operation")
    })
    @GetMapping("/api/user/show/{type}")
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
    @Operation(summary = "user info by ID",
               description = "아이디에 해당하는 유저 정보를 요청")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                         description = "successful operation",
                         content = @Content(schema = @Schema(implementation = User.class))),
            @ApiResponse(responseCode = "400",
                         description = "bad request operation")
    })
    @GetMapping("/api/user/show/{id}")
    public ResponseEntity<User> findUserById(@PathVariable Long id) {
        User user = userService.findUserById(id);
        return ResponseEntity
                .status(user != null ? HttpStatus.OK : HttpStatus.BAD_REQUEST)
                .body(user);
    }
}
