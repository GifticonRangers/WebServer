package com.capstone.webserver.api.subject;

import com.capstone.webserver.entity.attendance.Attendance;
import com.capstone.webserver.entity.subject.Subject;
import com.capstone.webserver.service.subject.SubjectService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.FileNotFoundException;
import java.util.ArrayList;

@RestController
@Slf4j
public class SubjectApiController {

    @Autowired
    SubjectService subjectService;


    /*
     * API Request: json에서 강좌 DB에 등록
     * permission: Admin
     */
    @GetMapping("/api/admin/subject/update")
    public ResponseEntity update() throws FileNotFoundException {
        subjectService.update();
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    /*
     * API Request: json에서 강좌 DB에 등록
     * permission: All user
     */
    @GetMapping("/api/admin/subject/show")
    public ResponseEntity<ArrayList<Subject>> show() throws FileNotFoundException {
        ArrayList<Subject> subjects = subjectService.show();
        return ResponseEntity.status(subjects != null ? HttpStatus.OK : HttpStatus.BAD_REQUEST).body(subjects);
    }
}
