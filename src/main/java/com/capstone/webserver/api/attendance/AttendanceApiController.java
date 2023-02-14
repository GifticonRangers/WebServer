package com.capstone.webserver.api.attendance;

import com.capstone.webserver.dto.user.AuditorForm;
import com.capstone.webserver.entity.attendance.Attendance;
import com.capstone.webserver.service.attendance.AttendanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
public class AttendanceApiController {
    @Autowired
    AttendanceService attendanceService;


    @PostMapping("/api/professor/subject/createAttendanceList/")
    public ResponseEntity<ArrayList<Attendance>> createAttendanceList(@RequestBody AuditorForm dto) {
        ArrayList<Attendance> attendanceArrayList = attendanceService.createAttendanceList(dto.getIdSubject(), dto.getId());
        return ResponseEntity
                .status(HttpStatus.OK)
                .build();
    }
}