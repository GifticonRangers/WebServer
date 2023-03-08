package com.capstone.webserver.entity.user;

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
public class Auditor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Schema(description = "User의 기본키값")
    @Column private Long idUser;

    @Schema(description = "Subject의 기본키값")
    @Column private Long idSubject;
}
