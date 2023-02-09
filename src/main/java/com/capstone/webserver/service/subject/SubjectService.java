package com.capstone.webserver.service.subject;

import com.capstone.webserver.entity.subject.Subject;
import com.capstone.webserver.entity.subject.GetSubjectJSONModel;
import com.capstone.webserver.repository.SubjectRepository;
import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.Reader;
import java.util.ArrayList;

@Service
@Slf4j
public class SubjectService {
    @Autowired
    SubjectRepository subjectRepository;

    /* DB에 과목 등록 */
    public void update() throws FileNotFoundException {
//        Reader reader = new FileReader("C:\\Users\\user\\hg_yellow\\capstone-designs\\WebServer\\src\\main\\resources\\json\\subject.json");
        Reader reader = new FileReader("D:\\INU-LECTURE\\WebServer\\src\\main\\resources\\json\\subject.json");
        Gson gson = new Gson();
        GetSubjectJSONModel subjects = gson.fromJson(reader, GetSubjectJSONModel.class);
        for(Subject subject: subjects.getSubject()){
            subjectRepository.save(subject);
            log.info("Subject: {}", subject.toString());
        }
    }


    /* 모든 과목 반환 */
    public ArrayList<Subject> show(){
        log.info("Request show: All");
        return subjectRepository.findAll();
    }
}
