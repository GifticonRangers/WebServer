package com.capstone.webserver.entity.user;

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

    @Column private Long idStudent;
    @Column private Long idSubject;
}
