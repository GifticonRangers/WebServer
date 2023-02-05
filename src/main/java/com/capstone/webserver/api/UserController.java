package com.capstone.webserver.api;

import com.capstone.webserver.entity.Professor;
import com.capstone.webserver.entity.Student;
import com.capstone.webserver.entity.User;
import com.capstone.webserver.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.embedded.undertow.UndertowWebServer;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;

@RestController
@Slf4j
public class UserController {
    @Autowired
    UserService userService;

    @GetMapping("/api/show/student")
    public ResponseEntity<ArrayList<Student>> showStudents(){
        ArrayList<Student> std = userService.showStudents();
        return ResponseEntity.status(std != null ? HttpStatus.OK : HttpStatus.BAD_REQUEST).body(std);
    }

    @GetMapping("/api/show/student/{id}")
    public ResponseEntity<Student> showStudent(@PathVariable Long id){
        Student std = userService.showStudent(id);
        return ResponseEntity.status(std != null ? HttpStatus.OK : HttpStatus.BAD_REQUEST).body(std);
    }

    @GetMapping("/api/show/professor")
    public ResponseEntity<ArrayList<Professor>> showProfessors(){
        ArrayList<Professor> pf = userService.showProfessors();
        return ResponseEntity.status(pf != null ? HttpStatus.OK : HttpStatus.BAD_REQUEST).body(pf);
    }

    @GetMapping("/api/show/professor/{id}")
    public ResponseEntity<Professor> showProfessor(@PathVariable Long id){
        Professor pf = userService.showProfessor(id);
        return ResponseEntity.status(pf != null ? HttpStatus.OK : HttpStatus.BAD_REQUEST).body(pf);
    }
}
