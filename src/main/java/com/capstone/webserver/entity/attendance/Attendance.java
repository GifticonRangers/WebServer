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
    @Column
    private Long id;

    @Schema(description = "출석 날짜")
    @Column private String dateAttendance;

    @Schema(description = "출석 주차")
    @Column private String weekAttendance;

    @Schema(description = "출석 차시")
    @Column private String timeAttendance;

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

    @Schema(description = "출석 시작을 나타냄")
    @Column private String startAttendance;

    @Schema(description = "출석 끝냄을 나타냄")
    @Column private String endAttendance;

    @Schema(description = "출석 횟수를 나타냄")
    @Column private int nfcCount;

    public void patch(Attendance target) {
        if (target.dateAttendance != null)
            this.dateAttendance = target.dateAttendance;

        if (target.weekAttendance != null)
            this.weekAttendance = target.weekAttendance;

        if (target.timeAttendance != null)
            this.timeAttendance = target.timeAttendance;

        if (target.stateAttendance != null)
            this.stateAttendance = target.stateAttendance;

        if (target.idProfessor != null)
            this.idProfessor = target.idProfessor;

        if (target.idStudent != null)
            this.idStudent = target.idStudent;

        if (target.idSubject != null)
            this.idSubject = target.idSubject;

        if (target.startAttendance != null)
            this.startAttendance = target.startAttendance;
    }
}