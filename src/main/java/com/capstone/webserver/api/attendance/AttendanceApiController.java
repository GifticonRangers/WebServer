package com.capstone.webserver.api.attendance;

import com.capstone.webserver.entity.attendance.Attendance;
import com.capstone.webserver.service.attendance.AttendanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;

@RestController
public class AttendanceApiController {
    @Autowired
    AttendanceService attendanceService;


    @GetMapping("/api/professor/subject/createAttendanceList/{subjectId}")
    public ResponseEntity<ArrayList<Attendance>> createAttendanceList(@PathVariable Long subjectId) {
        ArrayList<Attendance> attendanceArrayList = attendanceService.createAttendanceList(subjectId);
        return ResponseEntity
                .status(HttpStatus.OK)
                .build();
    }
}