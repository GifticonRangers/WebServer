package com.capstone.webserver.entity.subject;

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
public class Subject {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "아이디")
    private Long id;

    @Column
    @Schema(description = "강좌의 해당 년도")
    private String yearSubject;

    @Column
    @Schema(description = "강좌의 해당 학기")
    private String semesterSubject;

    @Column
    @Schema(description = "학수번호")
    private String idSubject;

    @Column
    @Schema(description = "단과대학")
    private String univSubject;

    @Column
    @Schema(description = "학과/부")
    private String majorSubject;

    @Column
    @Schema(description = "강좌 종류")
    private String typeSubject;

    @Column
    @Schema(description = "강좌명")
    private String nameSubject;

    @Column
    @Schema(description = "교수명")
    private String profSubject;

    @Column
    @Schema(description = "시간표")
    private String timeSubject;

    @Column
    @Schema(description = "학점")
    private String creditSubject;
}
