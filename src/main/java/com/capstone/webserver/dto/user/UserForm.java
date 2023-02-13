package com.capstone.webserver.dto.user;

import com.capstone.webserver.entity.user.Gender;
import com.capstone.webserver.entity.user.Role;
import com.capstone.webserver.entity.user.User;
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
    private int gender;

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
                .genderUser(Gender.values()[gender])
                .build();
    }
}
