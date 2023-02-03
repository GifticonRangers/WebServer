package com.capstone.webserver.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Getter
public class Student extends User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column private String idStudent;
    @Column private String pwStudent;
    @Column private String nameStudent;
    @Column private String phoneStudent;
    @Column private String emailStudent;
    @Column private String dptStudent;
}
