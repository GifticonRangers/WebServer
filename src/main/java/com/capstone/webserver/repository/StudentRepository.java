package com.capstone.webserver.repository;

import com.capstone.webserver.entity.Student;
import org.springframework.data.repository.CrudRepository;

import java.util.ArrayList;

public interface StudentRepository extends CrudRepository<Student, Long> {
    ArrayList<Student> findAll();
}
