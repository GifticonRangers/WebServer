package com.capstone.webserver.dto.user;

import com.capstone.webserver.entity.user.Gender;
import com.capstone.webserver.entity.user.Role;
import com.capstone.webserver.entity.user.User;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@ToString
@Getter
public class UserForm {
    @Schema(description = "기본키")
    private Long id;

    @Schema(description = "학번/교번")
    private String idUser;

    @Schema(description = "비밀번호")
    private String pwUser;

    @Schema(description = "이름")
    private String name;

    @Schema(description = "전화번호")
    private String phone;

    @Schema(description = "이메일")
    private String email;

    @Schema(description = "학과")
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
