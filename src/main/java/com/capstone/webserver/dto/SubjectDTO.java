package com.capstone.webserver.dto;

import com.capstone.webserver.entity.user.Gender;
import com.capstone.webserver.entity.user.Role;
import com.capstone.webserver.entity.user.User;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.Column;


public class SubjectDTO {
    @AllArgsConstructor
    @NoArgsConstructor
    @ToString
    @Getter
    public static class SubjectForm {
        @Schema(description = "아이디")
        private Long id;

        @Schema(description = "강좌의 해당 년도")
        private String yearSubject;

        @Schema(description = "강좌의 해당 학기")
        private String semesterSubject;

        @Schema(description = "학수번호")
        private String idSubject;

        @Schema(description = "단과대학")
        private String univSubject;

        @Schema(description = "학과/부")
        private String majorSubject;

        @Schema(description = "강좌 종류")
        private String typeSubject;

        @Schema(description = "강좌명")
        private String nameSubject;

        @Schema(description = "교수명")
        private String profSubject;

        @Schema(description = "시간표")
        private String timeSubject;

        @Schema(description = "학점")
        private String creditSubject;
    }
}
