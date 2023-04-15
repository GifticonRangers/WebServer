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
        ArrayList<Attendance> attendanceArrayList = attendanceService.createAttendanceList(dto);

        return ResponseEntity
                .status(attendanceArrayList != null ? HttpStatus.OK : HttpStatus.BAD_REQUEST)
                .body(attendanceArrayList);
    }

    @Operation(summary = "Update Attendance State",
            description = "수강생의 출석현황 수정\n\n"
                    + "id(기본키값), stateAttendance(출석 상태) 입력 필요")
    @PostMapping("/api/attendance/updateAttendance")
    public ResponseEntity<Attendance> updateAttendance(@RequestBody AttendanceDTO.updateAttendanceForm dto) {
        Attendance attendance = attendanceService.updateAttendance(dto);

        return ResponseEntity
                .status(attendance != null ? HttpStatus.OK : HttpStatus.BAD_REQUEST)
                .body(attendance);
    }

    @Operation(summary = "Show Time Attendance",
            description = "특정 주차의 차시에 따른 출석 리스트 반환\n\n"
                    + "weekAttendance, timeAttendance, idSubject(강좌 기본키값) 입력 필요")
    @PostMapping("/api/attendance/showAttendanceByTime")
    public ResponseEntity<ArrayList<Attendance>> showAttendanceByTime(@RequestBody AttendanceDTO.showAttendanceForm dto) {
        ArrayList<Attendance> attendance = attendanceService.showAttendanceByTime(dto);

        return ResponseEntity
                .status(attendance != null ? HttpStatus.OK : HttpStatus.BAD_REQUEST)
                .body(attendance);
    }

    @Operation(summary = "Check Attendance State",
            description = "유저의 특정 과목 출석 정보 열람\n\n"
                    + "idStudent(학생 기본키값), idSubject(강좌 기본키값) 입력 필요")
    @PostMapping("/api/attendance/showAttendanceByUser")
    public ResponseEntity<ArrayList<Attendance>> showAttendanceByUser(@RequestBody AttendanceDTO.checkAttendanceForm dto) {
        ArrayList<Attendance> attendance = attendanceService.showAttendanceByUser(dto);

        return ResponseEntity
                .status(attendance != null ? HttpStatus.OK : HttpStatus.BAD_REQUEST)
                .body(attendance);
    }

    @Operation(summary = "Check students who participated in attendance",
            description = "출결에 참여한 학생 수 반환\n\n"
                    + "weekAttendance, timeAttendance, idSubject(강좌 기본키값) 입력 필요")
    @PostMapping("/api/attendance/showAttendanceInfo")
    public ResponseEntity<AttendanceDTO.AttendanceInfoForm> showAttendanceInfo(@RequestBody AttendanceDTO.showAttendanceForm dto) {
        AttendanceDTO.AttendanceInfoForm attendanceInfo = attendanceService.showAttendanceInfo(dto);

        return ResponseEntity
                .status(attendanceInfo != null ? HttpStatus.OK : HttpStatus.BAD_REQUEST)
                .body(attendanceInfo);
    }


    @Operation(summary = "Check attendance date list",
            description = "출결에 차시 리스트 반환\n\n"
                    + "idUser(유저의 기본키), idSubject(강좌의 기본키)")
    @PostMapping("/api/attendance/showAttendanceTimeList")
    public ResponseEntity<ArrayList<AttendanceDTO.DateForm>> showAttendanceTimeList(@RequestBody UserDTO.UserSubjectInfoForm dto) {
        ArrayList<AttendanceDTO.DateForm> dateForms = attendanceService.showAttendanceTimeList(dto);

        return ResponseEntity
                .status(dateForms != null ? HttpStatus.OK : HttpStatus.BAD_REQUEST)
                .body(dateForms);
    }
}