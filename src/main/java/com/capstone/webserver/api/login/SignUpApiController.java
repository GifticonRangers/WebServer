package com.capstone.webserver.api.login;

import com.capstone.webserver.dto.UserDTO;
import com.capstone.webserver.entity.user.User;
import com.capstone.webserver.service.login.SignUpService;
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
import org.springframework.web.bind.annotation.*;


@RestController
@Slf4j
@Tag(name = "login", description = "회원가입 API")
public class SignUpApiController {
    @Autowired
    SignUpService signUpService;

    /*
     * API Request: 회원가입
     * permission: All User
     */
    @Operation(summary = "sign-up page",
               description = "회원가입에 필요한 정보를 입력")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "successful operation",
                    content = @Content(schema = @Schema(implementation = User.class))),
            @ApiResponse(responseCode = "400",
                    description = "bad request operation")
    })
    @PostMapping("/api/login/signup")
    public ResponseEntity<User> signup(@RequestBody UserDTO.UserForm dto) {
        User user = signUpService.signup(dto);

        return ResponseEntity
                .status(user != null ? HttpStatus.OK : HttpStatus.BAD_REQUEST)
                .body(user);
    }


    /*
     * API Request: id 중복 체크 확인
     * permission: ADMIN
     */
    @Operation(summary = "Check duplicated-id",
               description = "ID 중복 체크 확인, 회원가입에서 바로 사용")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "successful operation"),
            @ApiResponse(responseCode = "400",
                    description = "bad request operation")
    })
    @PostMapping("/api/login/checkId")
    public ResponseEntity<Boolean> checkDuplicateId(@RequestBody UserDTO.UserForm dto) {
        Boolean check = signUpService.checkDuplicateId(dto.getIdUser());
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(check);
    }
}
