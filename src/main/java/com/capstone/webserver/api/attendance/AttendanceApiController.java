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


    @Operation(summary = "Create Attendance List",
            description = "강좌에 대한 출결현황 테이블 구성\n\n"
                        + "idUser(교수 기본키값), idSubject(강좌 기본키값) 입력 필요")
    @PostMapping("/api/attendance/createAttendanceList")
    public ResponseEntity<ArrayList<Attendance>> createAttendanceList(@RequestBody UserDTO.UserSubjectInfoForm dto) {
        ArrayList<Attendance> attendanceArrayList = attendanceService.createAttendanceList(dto.getIdSubject(), dto.getIdUser());

        return ResponseEntity
                .status(attendanceArrayList != null ? HttpStatus.OK : HttpStatus.BAD_REQUEST)
                .body(attendanceArrayList);
    }

    @Operation(summary = "Update Attendance State",
            description = "수강생의 출석현황 수정\n\n"
                        + "dateAttendance, weekAttendance, timeAttendance, stateAttendance 입력 필요")
    @PostMapping("/api/attendance/updateAttendance")
    public ResponseEntity<Attendance> updateAttendance(@RequestBody AttendanceDTO.AttendanceForm dto) {
        Attendance attendance = attendanceService.updateAttendance(dto);

        return ResponseEntity
                .status(attendance != null ? HttpStatus.OK : HttpStatus.BAD_REQUEST)
                .body(attendance);
    }

    @Operation(summary = "Change Time",
            description = "특정 주차의 차시 변경\n\n"
                        + "weekAttendance, timeAttendance, idSubject(강좌 기본키값) 입력 필요")
    @PostMapping("/api/attendance/showAttendanceByTime")
    public ResponseEntity<ArrayList<Attendance>> showAttendanceByTime(@RequestBody AttendanceDTO.AttendanceForm dto) {
        ArrayList<Attendance> attendance = attendanceService.showAttendanceByTime(dto);

        return ResponseEntity
                .status(attendance != null ? HttpStatus.OK : HttpStatus.BAD_REQUEST)
                .body(attendance);
    }

    @Operation(summary = "Check Attendance State",
            description = "유저의 특정 과목 출석 정보 열람\n\n"
                        + "idStudent(학생 기본키값), idSubject(강좌 기본키값) 입력 필요")
    @PostMapping("/api/attendance/showAttendanceByUser")
    public ResponseEntity<ArrayList<Attendance>> showAttendanceByUser(@RequestBody AttendanceDTO.AttendanceForm dto) {
        ArrayList<Attendance> attendance = attendanceService.showAttendanceByUser(dto);

        return ResponseEntity
                .status(attendance != null ? HttpStatus.OK : HttpStatus.BAD_REQUEST)
                .body(attendance);
    }
}