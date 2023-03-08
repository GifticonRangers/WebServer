package com.capstone.webserver.entity.user;

import com.capstone.webserver.entity.user.Role;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import javax.persistence.*;

@Entity
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    @Enumerated(EnumType.STRING)
    @Schema(description = "유저 종류")
    private Role typeUser;

    @Schema(description = "학번/교번")
    @Column(unique = true) private String idUser;

    @Schema(description = "비밀번호")
    @Column private String pwUser;

    @Schema(description = "이름")
    @Column private String nameUser;

    @Schema(description = "전화번호")
    @Column private String phoneUser;

    @Schema(description = "이메일")
    @Column private String emailUser;

    @Schema(description = "학과")
    @Column private String dptUser;

    @Schema(description = "성별")
    @Column
    @Enumerated(EnumType.STRING)
    private Gender genderUser;
}
