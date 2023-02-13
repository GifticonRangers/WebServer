package com.capstone.webserver.entity.user;

import com.capstone.webserver.entity.user.Role;
import lombok.*;

import javax.persistence.*;

@Entity
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
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
    @Column
    @Enumerated(EnumType.STRING)
    private Gender genderUser;
}
