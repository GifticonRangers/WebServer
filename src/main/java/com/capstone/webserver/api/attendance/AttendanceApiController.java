package com.capstone.webserver.api.attendance;

import com.capstone.webserver.dto.AttendanceDTO;
import com.capstone.webserver.dto.UserDTO;
import com.capstone.webserver.entity.attendance.Attendance;
import com.capstone.webserver.service.attendance.AttendanceService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.Array;
import java.util.ArrayList;

@Tag(name = "attendance", description = "attendance 등록 api")
@RestController
public class AttendanceApiController {
    @Autowired
    AttendanceService attendanceService;


    @Operation(summary = "출결현황 등록",
            description = "강좌를 듣는 수강생의 출결현황 등록")
    @PostMapping("/api/attendance/createAttendanceList")
    public ResponseEntity<ArrayList<Attendance>> createAttendanceList(@RequestBody UserDTO.UserSubjectInfoForm dto) {
        ArrayList<Attendance> attendanceArrayList = attendanceService.createAttendanceList(dto.getIdSubject(), dto.getIdUser());

        return ResponseEntity
                .status(attendanceArrayList != null ? HttpStatus.OK : HttpStatus.BAD_REQUEST)
                .body(attendanceArrayList);
    }

    @Operation(summary = "출결현황 수정",
            description = "수강생의 출석현황 수정")
    @PostMapping("/api/attendance/updateAttendance")
    public ResponseEntity<Attendance> updateAttendance(@RequestBody AttendanceDTO.AttendanceForm dto) {
        Attendance attendance = attendanceService.updateAttendance(dto);

        return ResponseEntity
                .status(attendance != null ? HttpStatus.OK : HttpStatus.BAD_REQUEST)
                .body(attendance);
    }

    @Operation(summary = "차시 변경",
            description = "특정 주차의 차시 변경")
    @PostMapping("/api/attendance/showAttendanceByTime")
    public ResponseEntity<ArrayList<Attendance>> showAttendanceByTime(@RequestBody AttendanceDTO.AttendanceForm dto) {
        ArrayList<Attendance> attendance = attendanceService.showAttendanceByTime(dto);

        return ResponseEntity
                .status(attendance != null ? HttpStatus.OK : HttpStatus.BAD_REQUEST)
                .body(attendance);
    }

    @Operation(summary = "출석 정보 확인",
            description = "유저의 특정 과목의 출석 정보 열람")
    @PostMapping("/api/attendance/showAttendanceByUser")
    public ResponseEntity<ArrayList<Attendance>> showAttendanceByUser(@RequestBody AttendanceDTO.AttendanceForm dto) {
        ArrayList<Attendance> attendance = attendanceService.showAttendanceByUser(dto);

        return ResponseEntity
                .status(attendance != null ? HttpStatus.OK : HttpStatus.BAD_REQUEST)
                .body(attendance);
    }
}