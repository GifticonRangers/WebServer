package com.capstone.webserver.api;

import com.capstone.webserver.service.SubjectService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class SubjectController {

    @Autowired
    SubjectService subjectService;


}
