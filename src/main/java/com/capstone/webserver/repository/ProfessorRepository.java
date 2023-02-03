package com.capstone.webserver.repository;

import com.capstone.webserver.entity.Professor;
import org.springframework.data.repository.CrudRepository;

import java.util.ArrayList;

public interface ProfessorRepository extends CrudRepository<Professor, Long> {
    ArrayList<Professor> findAll();
}