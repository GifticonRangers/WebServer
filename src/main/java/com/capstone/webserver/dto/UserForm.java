package com.capstone.webserver.dto;

import com.capstone.webserver.entity.Professor;
import com.capstone.webserver.entity.Student;
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

    public Student toStudentEntity() { return new Student(id, idUser, pwUser, name, phone, email, department); }

    public Professor toProfessorEntity(){
        return new Professor(id, idUser, pwUser, name, phone, email, department);
    }
}
