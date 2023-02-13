package com.capstone.webserver.service.attendance;

import com.capstone.webserver.entity.attendance.Attendance;
import com.capstone.webserver.entity.subject.Subject;
import com.capstone.webserver.repository.AttendanceRepository;
import com.capstone.webserver.repository.SubjectRepository;
import com.capstone.webserver.util.DateUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Slf4j
@Service
public class AttendanceService {
    @Autowired
    AttendanceRepository attendanceRepository;
    @Autowired
    SubjectRepository subjectRepository;

    public ArrayList<Attendance> createAttendanceList(Long subjectId) {
        Subject target = subjectRepository.findById(subjectId).orElse(null);
        if (target != null) {
            DateUtil.computeDate(
                    Integer.parseInt(target.getYearSubject()),
                    target.getSemesterSubject(),
                    target.getTimeSubject()
            );

            return null;
        }
        else {
            log.error("computeDate Failed: Not found Subject id");
            return null;
        }
    }
}
