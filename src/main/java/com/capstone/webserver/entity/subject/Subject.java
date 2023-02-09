package com.capstone.webserver.entity.subject;

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
    private Long id;

    @Column private String yearSubject;
    @Column private String semesterSubject;
    @Column private String idSubject;
    @Column private String univSubject;
    @Column private String majorSubject;
    @Column private String typeSubject;
    @Column private String nameSubject;
    @Column private String profSubject;
    @Column private String timeSubject;
    @Column private String creditSubject;
}
