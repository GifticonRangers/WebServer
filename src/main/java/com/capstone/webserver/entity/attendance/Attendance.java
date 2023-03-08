package com.capstone.webserver.entity.attendance;

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
public class Attendance {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Schema(description = "출석 날짜")
    @Column private String dateAttendance;

    @Schema(description = "출석 상태")
    @Column
    @Enumerated(EnumType.STRING)
    private State stateAttendance;

    @Schema(description = "Professor의 기본키값")
    @Column private Long idProfessor;

    @Schema(description = "Student의 기본키값")
    @Column private Long idStudent;

    @Schema(description = "Subject의 기본키값")
    @Column private Long idSubject;
}