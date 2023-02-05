package com.capstone.webserver.entity;

import lombok.Builder;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Builder
@NoArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column private String idUser;
    @Column private String pwUser;
    @Column private String nameUser;
    @Column private String phoneUser;
    @Column private String emailUser;
    @Column private String dptUser;
    @Column private int typeUser;

    public User(Long id, String idUser, String pwUser, String nameUser, String phoneUser, String emailUser, String dptUser, Role typeUser) {
        this.id = id;
        this.idUser = idUser;
        this.pwUser = pwUser;
        this.nameUser = nameUser;
        this.phoneUser = phoneUser;
        this.emailUser = emailUser;
        this.dptUser = dptUser;
        this.typeUser = 0;
    }
}