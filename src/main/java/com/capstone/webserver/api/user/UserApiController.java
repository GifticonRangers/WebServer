package com.capstone.webserver.api.user;

import com.capstone.webserver.dto.AttendanceDTO;
import com.capstone.webserver.dto.SubjectDTO;
import com.capstone.webserver.dto.UserDTO;
import com.capstone.webserver.entity.user.Role;
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
import org.springframework.web.bind.annotation.*;

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
    @Operation(summary = "Show all User Info",
               description = "전체 유저 정보")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                         description = "successful operation",
                         content = @Content(schema = @Schema(implementation = User.class))),
            @ApiResponse(responseCode = "400",
                         description = "bad request operation")
    })
    @GetMapping("/api/user/showAllUser")
    public ResponseEntity<ArrayList<User>> showAllUser() {
        ArrayList<User> user = userService.showAllUser();
        return ResponseEntity
                .status(user != null ? HttpStatus.OK : HttpStatus.BAD_REQUEST)
                .body(user);
    }

    /*
     * API Request: 타입별 모든 유저 정보 요청
     * permission: ADMIN
     */
    @Operation(summary = "Show all User Info by Type",
               description = "타입별 유저 정보\n\n"
                            + "type(역할) 입력 필요")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                         description = "successful operation",
                         content = @Content(schema = @Schema(implementation = User.class))),
            @ApiResponse(responseCode = "400",
                         description = "bad request operation")
    })
    @PostMapping("/api/user/showAllUserByTypeUser")
    public ResponseEntity<ArrayList<User>> showAllUserByTypeUser(@RequestBody UserDTO.typeUserForm dto) {
        ArrayList<User> user = userService.showAllUserByTypeUser(Role.values()[dto.getType()].toString());
        return ResponseEntity
                .status(user != null ? HttpStatus.OK : HttpStatus.BAD_REQUEST)
                .body(user);
    }

    /*
     * API Request: 특정 유저 정보 요청
     * permission: ADMIN
     */
    @Operation(summary = "Show User Info by ID",
               description = "아이디에 해당하는 유저 정보"
                            + "id(기본키값) 입력 필요")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                         description = "successful operation",
                         content = @Content(schema = @Schema(implementation = User.class))),
            @ApiResponse(responseCode = "400",
                         description = "bad request operation")
    })
    @PostMapping("/api/user/showUserByIdUser")
    public ResponseEntity<User> showUserById(@RequestBody UserDTO.userIdForm dto) {
        User user = userService.showUserById(dto.getId());
        return ResponseEntity
                .status(user != null ? HttpStatus.OK : HttpStatus.BAD_REQUEST)
                .body(user);
    }

    /*
     * API Request: 특정 과목별 수강생 조회
     * permission: Professor
     */
    @Operation(summary = "Search User by specific Subject",
            description = "Auditor DB에서 조회"
                        + "id(기본키값) 입력 필요")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "successful operation",
                    content = @Content(schema = @Schema(implementation = User.class))),
            @ApiResponse(responseCode = "400",
                    description = "bad request operation")
    })
    @PostMapping("/api/user/showUserBySubjectId")
    public ResponseEntity<ArrayList<User>> showUserBySubjectId(@RequestBody SubjectDTO.SubjectForm dto) {
        ArrayList<User> users = userService.showUserBySubjectId(dto);
        return ResponseEntity
                .status(users != null ? HttpStatus.OK : HttpStatus.BAD_REQUEST)
                .body(users);
    }


    @Operation(summary = "Show Time Attendance",
            description = "특정 주차의 차시에 따른 출석 리스트 반환\n\n"
                    + "weekAttendance, timeAttendance, idSubject(강좌 기본키값) 입력 필요")
    @PostMapping("/api/user/showUserAttendanceBySubjectId")
    public ResponseEntity<ArrayList<UserDTO.UserAttendanceForm>> showUserAttendanceBySubjectId(@RequestBody AttendanceDTO.showAttendanceForm dto) {
        ArrayList<UserDTO.UserAttendanceForm> userAttendanceForms = userService.showUserAttendanceBySubjectId(dto);
        return ResponseEntity
                .status(userAttendanceForms != null ? HttpStatus.OK : HttpStatus.BAD_REQUEST)
                .body(userAttendanceForms);
    }
}
