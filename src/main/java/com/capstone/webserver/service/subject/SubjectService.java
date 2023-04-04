package com.capstone.webserver.service.subject;

import com.capstone.webserver.common.util.DateUtil;
import com.capstone.webserver.common.util.SubjectUtil;
import com.capstone.webserver.dto.SubjectDTO;
import com.capstone.webserver.dto.UserDTO;
import com.capstone.webserver.entity.attendance.Attendance;
import com.capstone.webserver.entity.subject.Subject;
import com.capstone.webserver.entity.subject.GetSubjectJSONModel;
import com.capstone.webserver.entity.user.Auditor;
import com.capstone.webserver.entity.user.Role;
import com.capstone.webserver.entity.user.User;
import com.capstone.webserver.repository.AttendanceRepository;
import com.capstone.webserver.repository.AuditorRepository;
import com.capstone.webserver.repository.SubjectRepository;
import com.capstone.webserver.repository.UserRepository;
import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Map;

@Service
@Slf4j
public class SubjectService {
    @Autowired
    SubjectRepository subjectRepository;

    @Autowired
    AuditorRepository auditorRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    AttendanceRepository attendanceRepository;

    /* DB에 과목 등록 */
    public void update() throws FileNotFoundException {
        Reader reader = new FileReader("C:\\Users\\user\\hg_yellow\\capstone-designs\\WebServer\\src\\main\\resources\\json\\subject.json");
//        Reader reader = new FileReader("D:\\INU-LECTURE\\WebServer\\src\\main\\resources\\json\\subject.json");
        Gson gson = new Gson();
        GetSubjectJSONModel subjects = gson.fromJson(reader, GetSubjectJSONModel.class);
        for (Subject subject : subjects.getSubject()) {
            subjectRepository.save(subject);
            log.info("Subject: {}", subject.toString());
        }
    }


    /* 모든 과목 반환 */
    public ArrayList<Subject> show() {
        log.info("Request show: All");
        return subjectRepository.findAll();
    }

    /* 특정 유저가 듣는 과목 정보 반환 */
    public ArrayList<Subject> showSubjectByUserId(UserDTO.UserForm dto) {
        Long id = dto.getId();

        if (id == null) {
            log.error("Error: Not found id");
            return null;
        }

        ArrayList<Auditor> auditors = auditorRepository.findAllByIdUser(id);
        ArrayList<Subject> subjects = new ArrayList<Subject>();

        for (Auditor auditor : auditors)
            subjects.add(
                    subjectRepository
                            .findById(auditor.getIdSubject())
                            .orElse(null)
            );


        return subjects;
    }


    public ArrayList<SubjectDTO.TodaySubjectForm> showTodaySubjectByUserId(UserDTO.UserForm dto) {
        ArrayList<Attendance> attendanceArrayList = attendanceRepository.findAllByIdStudent(dto.getId());
        ArrayList<Subject> subjectArrayList = showSubjectByUserId(dto);

        return SubjectUtil.createTodaySubjectList(attendanceArrayList, subjectArrayList);
    }

    public ArrayList<SubjectDTO.ScheduleSubjectForm> showScheduleSubjectByUserId(UserDTO.UserForm dto) {
        Long id = dto.getId();

        if (id == null) {
            log.error("Error: Not found id");
            return null;
        }

        ArrayList<Auditor> auditors = auditorRepository.findAllByIdUser(id);
        ArrayList<SubjectDTO.ScheduleSubjectForm> scheduleSubjectForms = new ArrayList<SubjectDTO.ScheduleSubjectForm>();

        for (Auditor auditor : auditors) {
            Subject subject = subjectRepository.findById(auditor.getIdSubject()).orElse(null);
            Map<String, Map<String, ArrayList<String>>> locationDayTime = SubjectUtil.splitLocationTime(subject.getTimeSubject());
            for (String loc : locationDayTime.keySet())
                for (String day : locationDayTime.get(loc).keySet()) {
                    ArrayList<String> times = locationDayTime.get(loc).get(day);
                    String startTime = times.get(0);
                    String endTime = times.get(times.size()-1);

                    scheduleSubjectForms.add(
                            SubjectDTO.ScheduleSubjectForm
                                    .builder()
                                    .id(subject.getId())
                                    .nameSubject(subject.getNameSubject())
                                    .profSubject(subject.getProfSubject())
                                    .locationSubject(loc)
                                    .daySubject(day)
                                    .startTimeSubject(DateUtil.getStartTime("("+startTime+")"))
                                    .endTimeSubject(DateUtil.getEndTime("("+endTime+")"))
                                    .build()
                    );
                }
        }

        return scheduleSubjectForms;
    }
}
