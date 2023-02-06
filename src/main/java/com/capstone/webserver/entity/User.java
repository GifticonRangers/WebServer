package com.capstone.webserver.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    @Enumerated(EnumType.STRING)
    private Role typeUser;
    @Column(unique = true) private String idUser;
    @Column private String pwUser;
    @Column private String nameUser;
    @Column private String phoneUser;
    @Column private String emailUser;
    @Column private String dptUser;
}