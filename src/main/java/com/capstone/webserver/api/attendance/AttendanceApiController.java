package com.capstone.webserver.api.attendance;

import com.capstone.webserver.dto.UserDTO;
import com.capstone.webserver.entity.attendance.Attendance;
import com.capstone.webserver.service.attendance.AttendanceService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@Tag(name = "attendance", description = "attendance 등록 api")
@RestController
public class AttendanceApiController {
    @Autowired
    AttendanceService attendanceService;


    @Operation(summary = "attendance 등록",
            description = "강좌를 듣는 수강생 attendance 등록")
    @PostMapping("/api/attendance/createAttendanceList")
    public ResponseEntity<ArrayList<Attendance>> createAttendanceList(@RequestBody UserDTO.UserSubjectInfoForm dto) {
        ArrayList<Attendance> attendanceArrayList = attendanceService.createAttendanceList(dto.getIdSubject(), dto.getIdUser());
        return ResponseEntity
                .status(HttpStatus.OK)
                .build();
    }
}