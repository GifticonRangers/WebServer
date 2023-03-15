package com.capstone.webserver.dto;

import com.capstone.webserver.entity.attendance.Attendance;
import com.capstone.webserver.entity.attendance.State;
import com.capstone.webserver.entity.user.Gender;
import com.capstone.webserver.entity.user.Role;
import com.capstone.webserver.entity.user.User;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.Column;

public class AttendanceDTO {
    @AllArgsConstructor
    @NoArgsConstructor
    @ToString
    @Getter
    public static class AttendanceForm {
        @Schema(description = "기본키값")
        private Long id;

        @Schema(description = "출석 날짜")
        private String dateAttendance;

        @Schema(description = "출석 주차")
        private String weekAttendance;

        @Schema(description = "출석 차시")
        private String timeAttendance;

        @Schema(description = "출석 상태")
        private int stateAttendance;

        @Schema(description = "Professor의 기본키값")
        private Long idProfessor;

        @Schema(description = "Student의 기본키값")
        private Long idStudent;

        @Schema(description = "Subject의 기본키값")
        private Long idSubject;

        public Attendance toEntity() {
            return Attendance.builder()
                    .id(id)
                    .dateAttendance(dateAttendance)
                    .weekAttendance(weekAttendance)
                    .timeAttendance(timeAttendance)
                    .stateAttendance(State.values()[stateAttendance])
                    .idProfessor(idProfessor)
                    .idStudent(idStudent)
                    .idSubject(idSubject)
                    .build();
        }
    }
}
