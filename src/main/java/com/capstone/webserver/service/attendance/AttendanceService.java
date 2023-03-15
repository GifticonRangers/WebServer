package com.capstone.webserver.service.attendance;

import com.capstone.webserver.dto.AttendanceDTO;
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
        ArrayList<Attendance> attendanceArrayList = new ArrayList<Attendance>();

        if (target != null) {
            Map<Integer, ArrayList<String>> schedules = DateUtil.computeDate(
                    Integer.parseInt(target.getYearSubject()),
                    target.getSemesterSubject(),
                    target.getTimeSubject()
            );

            ArrayList<Auditor> auditors = auditorRepository.findAllByIdSubject(subjectId);

            for (Auditor auditor : auditors) {
                for (int i = 1; i <= 16; i++) {
                    int j = 1;
                    for (String schedule : schedules.get(i)) {
                        Attendance entity = Attendance
                                .builder()
                                .id(null)
                                .dateAttendance(schedule)
                                .weekAttendance(String.format("%d", i))
                                .timeAttendance(String.format("%d", j))
                                .stateAttendance(State.ATTENDANCE)
                                .idProfessor(professorId)
                                .idStudent(auditor.getIdUser())
                                .idSubject(subjectId)
                                .build();

                        log.info("Attendance: {}", entity.toString());
                        Attendance save = attendanceRepository.save(entity);
                        attendanceArrayList.add(save);

                        j++;
                    }
                }
            }

            return attendanceArrayList;
        } else {
            log.error("computeDate Failed: Not found Subject id");
            return null;
        }
    }

    public Attendance updateAttendance(AttendanceDTO.AttendanceForm dto) {
        Attendance attendance = dto.toEntity();
        Attendance target = attendanceRepository.findById(attendance.getId()).orElse(null);

        if (target == null) {
            log.error("Error: Not found id");
            return null;
        }

        target.patch(attendance);

        return attendanceRepository.save(target);
    }

    public ArrayList<Attendance> showAttendanceByTime(AttendanceDTO.AttendanceForm dto) {
        String week = dto.getWeekAttendance();
        String time = dto.getTimeAttendance();
        Long idSubject = dto.getIdSubject();

        if (week == null || time == null || idSubject == null) {
            log.error("Error: Not found data");
            return null;
        }

        return attendanceRepository.findAllByWeekAttendanceAndTimeAttendanceAndIdSubject(week, time, idSubject);
    }

    public ArrayList<Attendance> showAttendanceByUser(AttendanceDTO.AttendanceForm dto) {
        Long idStudent = dto.getIdStudent();
        Long idSubject = dto.getIdSubject();

        if (idStudent == null || idSubject == null) {
            log.error("Error: Not found data");
            return null;
        }

        return attendanceRepository.findAllByIdStudentAndIdSubject(idStudent, idSubject);
    }
}
