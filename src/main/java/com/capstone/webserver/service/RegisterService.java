package com.capstone.webserver.service;

import com.capstone.webserver.dto.UserForm;
import com.capstone.webserver.entity.Professor;
import com.capstone.webserver.entity.Student;
import com.capstone.webserver.entity.User;
import com.capstone.webserver.repository.ProfessorRepository;
import com.capstone.webserver.repository.StudentRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class RegisterService {
    @Autowired
    StudentRepository studentRepository;
    @Autowired
    ProfessorRepository professorRepository;
    public User register(UserForm dto) {
        if(dto.getType() == 0) {
            Student std = dto.toStudentEntity();
            studentRepository.save(std);
            log.info("Student: {}", std.toString());
            return std;
        }

        else if(dto.getType() == 1){
            Professor pf = dto.toProfessorEntity();
            professorRepository.save(pf);
            log.info("Professor: {}", pf.toString());
            return pf;
        }

        else {
            log.error("Invalid request: type Error");
            return null;
        }
    }
}
