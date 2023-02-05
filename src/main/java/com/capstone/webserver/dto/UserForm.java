package com.capstone.webserver.dto;

import com.capstone.webserver.entity.Role;
import com.capstone.webserver.entity.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@ToString
@Getter
public class UserForm {
    private Long id;
    private String idUser;
    private String pwUser;
    private String name;
    private String phone;
    private String email;
    private String department;
    private int type;

    public User toEntity() {
        return User.builder()
                .id(id)
                .typeUser(Role.values()[type])
                .idUser(idUser)
                .pwUser(pwUser)
                .nameUser(name)
                .phoneUser(phone)
                .emailUser(email)
                .dptUser(department)
                .build();
    }
}
