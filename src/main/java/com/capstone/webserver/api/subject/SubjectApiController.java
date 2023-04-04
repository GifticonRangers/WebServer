package com.capstone.webserver.api.subject;

import com.capstone.webserver.dto.SubjectDTO;
import com.capstone.webserver.dto.UserDTO;
import com.capstone.webserver.entity.subject.Subject;
import com.capstone.webserver.entity.user.User;
import com.capstone.webserver.service.subject.SubjectService;
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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.FileNotFoundException;
import java.util.ArrayList;

@RestController
@Slf4j
@Tag(name = "Subject", description = "Subject 등록 및 조회 API")
public class SubjectApiController {

    @Autowired
    SubjectService subjectService;


    /*
     * API Request: json에서 강좌 DB에 등록
     * permission: Admin
     */

    @Operation(summary = "Update Subject DB",
               description = "subject.json을 읽어와 DB에 등록")
    @GetMapping("/api/subject/updateSubject")
    public ResponseEntity update() throws FileNotFoundException {
        subjectService.update();
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    /*
     * API Request: Subject 조회
     * permission: All user
     */
    @Operation(summary = "Show Subject DB",
               description = "subject DB에서 조회")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                         description = "successful operation",
                         content = @Content(schema = @Schema(implementation = Subject.class))),
            @ApiResponse(responseCode = "400",
                         description = "bad request operation")
    })
    @GetMapping("/api/subject/show")
    public ResponseEntity<ArrayList<Subject>> show() throws FileNotFoundException {
        ArrayList<Subject> subjects = subjectService.show();
        return ResponseEntity
                .status(subjects != null ? HttpStatus.OK : HttpStatus.BAD_REQUEST)
                .body(subjects);
    }

    /*
     * API Request: 유저별 Subject 조회
     * permission: All user
     */
    @Operation(summary = "Search Subject for a specific User",
            description = "Auditor DB에서 조회"
                        + "id(유저의 기본키값) 필요")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "successful operation",
                    content = @Content(schema = @Schema(implementation = Subject.class))),
            @ApiResponse(responseCode = "400",
                    description = "bad request operation")
    })
    @PostMapping("/api/subject/showSubjectByUserId")
    public ResponseEntity<ArrayList<Subject>> showSubjectByUserId(@RequestBody UserDTO.UserForm dto) {
        ArrayList<Subject> subjects = subjectService.showSubjectByUserId(dto);
        return ResponseEntity
                .status(subjects != null ? HttpStatus.OK : HttpStatus.BAD_REQUEST)
                .body(subjects);
    }

    /*
     * API Request: 오늘 날짜의 과목 조회
     * permission: All
     */
    @Operation(summary = "Search Subject by today Subject",
            description = "id(유저의 기본키값) 필요")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "successful operation",
                    content = @Content(schema = @Schema(implementation = Subject.class))),
            @ApiResponse(responseCode = "400",
                    description = "bad request operation")
    })
    @PostMapping("/api/subject/showTodaySubjectByUserId")
    public ResponseEntity<ArrayList<SubjectDTO.TodaySubjectForm>> showTodaySubjectByUserId(@RequestBody UserDTO.UserForm dto) {
        ArrayList<SubjectDTO.TodaySubjectForm> subjects = subjectService.showTodaySubjectByUserId(dto);
        return ResponseEntity
                .status(subjects != null ? HttpStatus.OK : HttpStatus.BAD_REQUEST)
                .body(subjects);
    }

    @Operation(summary = "Search Subject by schedule Subject",
            description = "id(유저의 기본키값) 필요")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "successful operation",
                    content = @Content(schema = @Schema(implementation = SubjectDTO.ScheduleSubjectForm.class))),
            @ApiResponse(responseCode = "400",
                    description = "bad request operation")
    })
    @PostMapping("/api/subject/showScheduleSubjectByUserId")
    public ResponseEntity<ArrayList<SubjectDTO.ScheduleSubjectForm>> showScheduleSubjectByUserId(@RequestBody UserDTO.UserForm dto) {
        ArrayList<SubjectDTO.ScheduleSubjectForm> scheduleSubjectForms = subjectService.showScheduleSubjectByUserId(dto);
        return ResponseEntity
                .status(scheduleSubjectForms != null ? HttpStatus.OK : HttpStatus.BAD_REQUEST)
                .body(scheduleSubjectForms);
    }
}
