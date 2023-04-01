package com.capstone.webserver.dto;

import com.capstone.webserver.entity.attendance.Attendance;
import com.capstone.webserver.entity.attendance.State;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

public class AttendanceDTO {
    @AllArgsConstructor
    @NoArgsConstructor
    @ToString
    @Getter
    public static class AttendanceForm {
        @Schema(description = "기본키값")
        private Long id;

        @Schema(description = "출석 날짜", example = "날짜")
        private String dateAttendance;

        @Schema(description = "출석 주차", example = "주차")
        private String weekAttendance;

        @Schema(description = "출석 차시", example = "차시")
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


    @AllArgsConstructor
    @NoArgsConstructor
    @ToString
    @Getter
    @Builder
    public static class AttendanceInfoForm{
        private int ATTENDANCE;
        private int LATE;
        private int ABSENCE;
        private int PUBLIC_ABSENCE;

        public void plusAttendance() { ATTENDANCE++; }
        public void plusLate() { LATE++; }
        public void plusAbsence() { ABSENCE++; }
        public void plusPublicAbsence() { PUBLIC_ABSENCE++; }
    }


    @AllArgsConstructor
    @NoArgsConstructor
    @ToString
    @Getter
    @Builder
    public static class DateForm {
        private String year;
        private String month;
        private String day;
        private String week;
        private String time;
    }
}
