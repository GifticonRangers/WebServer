package com.capstone.webserver.dto;

import com.capstone.webserver.entity.user.Gender;
import com.capstone.webserver.entity.user.Role;
import com.capstone.webserver.entity.user.User;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import javax.persistence.Column;


public class SubjectDTO {
    @AllArgsConstructor
    @NoArgsConstructor
    @ToString
    @Getter
    public static class SubjectForm {
        @Schema(description = "기본키")
        private Long id;

        @Schema(description = "강좌 개설연도", example = "강좌 개설연도")
        private String yearSubject;

        @Schema(description = "강좌 개설학기", example = "강좌 개설학기")
        private String semesterSubject;

        @Schema(description = "학수번호", example = "학수번호")
        private String idSubject;

        @Schema(description = "단과대학", example = "단과대학")
        private String univSubject;

        @Schema(description = "학과/부", example = "학과/부")
        private String majorSubject;

        @Schema(description = "강좌 종류", example = "전공심화/전공핵심/일반선택 등")
        private String typeSubject;

        @Schema(description = "강좌명", example = "강좌명")
        private String nameSubject;

        @Schema(description = "교수명", example = "교수명")
        private String profSubject;

        @Schema(description = "시간표", example = "시간표")
        private String timeSubject;

        @Schema(description = "학점", example = "이수학점")
        private String creditSubject;
    }

    @AllArgsConstructor
    @NoArgsConstructor
    @ToString
    @Getter
    @Builder
    public static class TodaySubjectForm {
        @Schema(description = "강의 기본키", example = "강의 기본키")
        private Long idSubject;

        @Schema(description = "강의 시작 시간", example = "강의 시작 시간")
        private String timeSubject;

        @Schema(description = "강좌명", example = "강좌명")
        private String nameSubject;

        @Schema(description = "교수명", example = "교수명")
        private String profSubject;

        @Schema(description = "강의실 위치", example = "101")
        private String locationSubject;

        @Schema(description = "주차", example = "1")
        private String weekSubject;
    }

    @AllArgsConstructor
    @NoArgsConstructor
    @ToString
    @Getter
    @Builder
    public static class ScheduleSubjectForm {
        @Schema(description = "강의 기본키", example = "강의 기본키")
        private Long id;

        @Schema(description = "강의 시작 시간", example = "강의 시작 시간")
        private String startTimeSubject;

        @Schema(description = "강의 종료 시간", example = "강의 종료 시간")
        private String endTimeSubject;

        @Schema(description = "강의 요일", example = "강의 요일")
        private String daySubject;

        @Schema(description = "강좌명", example = "강좌명")
        private String nameSubject;

        @Schema(description = "강의실 위치", example = "101")
        private String locationSubject;

        @Schema(description = "교수명", example = "교수명")
        private String profSubject;
    }
}
