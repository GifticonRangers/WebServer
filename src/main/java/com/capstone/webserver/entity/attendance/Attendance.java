package com.capstone.webserver.entity.attendance;

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
    @Column private String dateAttendance;
    @Column
    @Enumerated(EnumType.STRING)
    private State stateAttendance;
    @Column private Long idProfessor;
    @Column private Long idStudent;
    @Column private Long idSubject;
}