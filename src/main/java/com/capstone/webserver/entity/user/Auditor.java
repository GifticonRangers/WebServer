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
    private Long idStudent;

    @Column private Long idSubject;
}
