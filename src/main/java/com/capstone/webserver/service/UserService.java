package com.capstone.webserver.service;

import com.capstone.webserver.entity.Professor;
import com.capstone.webserver.entity.Student;
import com.capstone.webserver.entity.User;
import com.capstone.webserver.repository.ProfessorRepository;
import com.capstone.webserver.repository.StudentRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
@Slf4j
public class UserService {
    @Autowired
    StudentRepository studentRepository;
    @Autowired
    ProfessorRepository professorRepository;


    public ArrayList<Student> showStudents() {
        log.info("Request showStudent: All");
        return studentRepository.findAll();
    }

    public Student showStudent(Long id) {
        Student std = studentRepository.findById(id).orElse(null);

        if(std != null)
            log.info("Request showStudent: {}", std.toString());
        else
            log.error("Invalid request: Not found id");

        return std;
    }

    public ArrayList<Professor> showProfessors() {
        log.info("Request showProfessor: All");
        return professorRepository.findAll();
    }

    public Professor showProfessor(Long id) {
        Professor pf = professorRepository.findById(id).orElse(null);

        if(pf != null)
            log.info("Request showProfessor: {}", pf.toString());
        else
            log.error("Invalid request: Not found id");

        return pf;
    }
}
