package com.capstone.webserver.service.attendance;

import com.capstone.webserver.entity.attendance.Attendance;
import com.capstone.webserver.entity.attendance.State;
import com.capstone.webserver.entity.subject.Subject;
import com.capstone.webserver.entity.user.Auditor;
import com.capstone.webserver.entity.user.User;
import com.capstone.webserver.repository.AttendanceRepository;
import com.capstone.webserver.repository.AuditorRepository;
import com.capstone.webserver.repository.SubjectRepository;
import com.capstone.webserver.repository.UserRepository;
import com.capstone.webserver.util.DateUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.swing.*;
import java.util.ArrayList;
import java.util.Map;

@Slf4j
@Service
public class AttendanceService {
    @Autowired
    AttendanceRepository attendanceRepository;

    @Autowired
    SubjectRepository subjectRepository;

    @Autowired
    AuditorRepository auditorRepository;

    @Autowired
    UserRepository userRepository;

    public ArrayList<Attendance> createAttendanceList(Long subjectId, Long professorId) {
        Subject target = subjectRepository.findById(subjectId).orElse(null);
        if (target != null) {
            Map<Integer, ArrayList<String>> schedules = DateUtil.computeDate(
                    Integer.parseInt(target.getYearSubject()),
                    target.getSemesterSubject(),
                    target.getTimeSubject()
            );

            ArrayList<Auditor> auditors = auditorRepository.findAllByIdUser(subjectId);

            for(Auditor auditor: auditors){
                for (int i = 1; i <= 16; i++) {
                    for (String schedule: schedules.get(i)){
                        attendanceRepository.save(
                                Attendance
                                        .builder()
                                        .id(null)
                                        .dateAttendance(schedule)
                                        .stateAttendance(State.ATTENDANCE)
                                        .idProfessor(professorId)
                                        .idStudent(auditor.getIdUser())
                                        .idSubject(subjectId)
                                        .build());
                    }
                }
            }

            return null;
        }
        else {
            log.error("computeDate Failed: Not found Subject id");
            return null;
        }
    }
}
