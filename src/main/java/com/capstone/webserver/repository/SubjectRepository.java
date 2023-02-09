package com.capstone.webserver.repository;

import com.capstone.webserver.entity.subject.Subject;
import org.springframework.data.repository.CrudRepository;

import java.util.ArrayList;

public interface SubjectRepository extends CrudRepository<Subject, Long> {

    /* 모든 과목 반환 */
    ArrayList<Subject> findAll();
}
