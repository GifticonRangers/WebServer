package com.capstone.webserver.dto;

import com.capstone.webserver.entity.attendance.State;
import com.capstone.webserver.entity.user.Auditor;
import com.capstone.webserver.entity.user.Gender;
import com.capstone.webserver.entity.user.Role;
import com.capstone.webserver.entity.user.User;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.util.ArrayList;

public class UserDTO {
    @AllArgsConstructor
    @NoArgsConstructor
    @ToString
    @Getter
    public static class UserForm {
        @Schema(description = "기본키")
        private Long id;

        @Schema(description = "학번/교번", example = "학번/교번")
        private String idUser;

        @Schema(description = "비밀번호", example = "비밀번호")
        private String pwUser;

        @Schema(description = "이름", example = "이름")
        private String name;

        @Schema(description = "전화번호", example = "010-XXXX-XXXX")
        private String phone;

        @Schema(description = "이메일", example = "aaa@inu.ac.kr")
        private String email;

        @Schema(description = "학과", example = "ㅇㅇㅇ과")
        private String department;

        @Schema(description = "유저 종류")
        private int type;

        @Schema(description = "성별")
        private int gender;

        public User toEntity() {
            return User.builder()
                    .id(id)
                    .typeUser(Role.values()[type])
                    .idUser(idUser)
                    .pwUser(pwUser)
                    .nameUser(name)
                    .phoneUser(phone)
                    .emailUser(email)
                    .dptUser(department)
                    .genderUser(Gender.values()[gender])
                    .build();
        }
    }

    @AllArgsConstructor
    @NoArgsConstructor
    @ToString
    @Getter
    public static class UserSubjectInfoForm {
        @Schema(description="유저 기본키값")
        private Long idUser;
        @Schema(description="강좌 기본키값")
        private Long idSubject;

        public Auditor toEntity() {
            return Auditor
                    .builder()
//                    .id(null)
                    .idUser(idUser)
                    .idSubject(idSubject)
                    .build();
        }
    }

    @AllArgsConstructor
    @NoArgsConstructor
    @ToString
    @Getter
    public static class AddSubjectForm {
        @Schema(description="유저 기본키값")
        private Long idUser;
        @Schema(description="강좌 기본키값")
        private ArrayList<Long> subjects;

//        public Auditor toEntity() {
//            return Auditor
//                    .builder()
//                    .idUser(idUser)
//                    .idSubject(idSubject)
//                    .build();
//        }
    }

    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    @ToString
    @Getter
    public static class UserAttendanceForm {
        @Schema(description = "기본키")
        private Long id;

        @Schema(description = "학번/교번", example = "학번/교번")
        private String idUser;

        @Schema(description = "이름", example = "이름")
        private String name;

        @Schema(description = "출결", example = "출결")
        private State state;
    }

    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    @ToString
    @Getter
    public static class idUserForm {
        @Schema(description = "학번/교번", example = "학번/교번")
        private String idUser;

        public User toEntity() {
            return User.builder()
                    .idUser(idUser)
                    .build();
        }
    }

    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    @ToString
    @Getter
    public static class LoginForm {
        @Schema(description = "학번/교번", example = "학번/교번")
        private String idUser;

        @Schema(description = "비밀번호", example = "비밀번호")
        private String pwUser;

        public User toEntity() {
            return User.builder()
                    .idUser(idUser)
                    .pwUser(pwUser)
                    .build();
        }
    }

    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    @ToString
    @Getter
    public static class userIdForm {
        @Schema(description = "기본키값", example = "기본키값")
        private Long id;

        public User toEntity() {
            return User.builder()
                    .id(id)
                    .build();
        }
    }

    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    @ToString
    @Getter
    public static class typeUserForm {
        @Schema(description = "유저 종류")
        private int type;

        public User toEntity() {
            return User.builder()
                    .typeUser(Role.values()[type])
                    .build();
        }
    }
}
