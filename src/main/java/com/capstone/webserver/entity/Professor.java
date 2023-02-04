package com.capstone.webserver.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@ToString
@AllArgsConstructor
@Getter
@NoArgsConstructor
public class Professor extends User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column private String idProfessor;
    @Column private String pwProfessor;
    @Column private String nameProfessor;
    @Column private String phoneProfessor;
    @Column private String emailProfessor;
    @Column private String dptProfessor;
}
