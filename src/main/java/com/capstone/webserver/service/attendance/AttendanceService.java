package com.capstone.webserver.service.attendance;

import com.capstone.webserver.dto.AttendanceDTO;
import com.capstone.webserver.dto.UserDTO;
import com.capstone.webserver.entity.attendance.Attendance;
import com.capstone.webserver.entity.attendance.State;
import com.capstone.webserver.entity.subject.Subject;
import com.capstone.webserver.entity.user.Auditor;
import com.capstone.webserver.entity.user.Role;
import com.capstone.webserver.entity.user.User;
import com.capstone.webserver.repository.AttendanceRepository;
import com.capstone.webserver.repository.AuditorRepository;
import com.capstone.webserver.repository.SubjectRepository;
import com.capstone.webserver.repository.UserRepository;
import com.capstone.webserver.common.util.DateUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    public Attendance updateAttendance(AttendanceDTO.updateAttendanceForm dto) {
        Attendance attendance = dto.toEntity();
        Attendance target = attendanceRepository.findById(attendance.getId()).orElse(null);

        if (target == null) {
            log.error("Error: Not found id");
            return null;
        }

        target.patch(attendance);

        return attendanceRepository.save(target);
    }

    public ArrayList<Attendance> showAttendanceByTime(AttendanceDTO.showAttendanceForm dto) {
        String week = dto.getWeekAttendance();
        String time = dto.getTimeAttendance();
        Long idSubject = dto.getIdSubject();

        if (week == null || time == null || idSubject == null) {
            log.error("Error: Not found data");
            return null;
        }

        ArrayList<Attendance> attendances = new ArrayList<Attendance>();

        ArrayList<Attendance> target = attendanceRepository.findAllByWeekAttendanceAndTimeAttendanceAndIdSubject(week, time, idSubject);

        for (Attendance attendance: target)
            if (userRepository.findById(attendance.getIdStudent()).orElse(null).getTypeUser() == Role.STUDENT)
                attendances.add(attendance);


        return attendances;
    }

    public ArrayList<Attendance> showAttendanceByUser(AttendanceDTO.checkAttendanceForm dto) {
        Long idStudent = dto.getIdStudent();
        Long idSubject = dto.getIdSubject();

        if (idStudent == null || idSubject == null) {
            log.error("Error: Not found data");
            return null;
        }

        return attendanceRepository.findAllByIdStudentAndIdSubject(idStudent, idSubject);
    }

    public AttendanceDTO.AttendanceInfoForm showAttendanceInfo(AttendanceDTO.showAttendanceForm dto) {
        ArrayList<Attendance> attendances = attendanceRepository.findAllByWeekAttendanceAndTimeAttendanceAndIdSubject(dto.getWeekAttendance(), dto.getTimeAttendance(), dto.getIdSubject());
        AttendanceDTO.AttendanceInfoForm attendanceInfoForm = AttendanceDTO.AttendanceInfoForm
                                                                                        .builder()
                                                                                        .ATTENDANCE(0)
                                                                                        .LATE(0)
                                                                                        .ABSENCE(0)
                                                                                        .PUBLIC_ABSENCE(0)
                                                                                        .build();

        for (Attendance attendance: attendances) {
            User user = userRepository.findById(attendance.getIdStudent()).orElse(null);

            switch (attendance.getStateAttendance()) {
                case ATTENDANCE:
                    attendanceInfoForm.plusAttendance();
                    break;

                case LATE:
                    attendanceInfoForm.plusLate();
                    break;

                case ABSENCE:
                    attendanceInfoForm.plusAbsence();
                    break;

                case PUBLIC_ABSENCE:
                    attendanceInfoForm.plusPublicAbsence();
                    break;
            }
        }

        return attendanceInfoForm;
    }

    public ArrayList<AttendanceDTO.DateForm> showAttendanceTimeList(UserDTO.UserSubjectInfoForm dto) {
        ArrayList<Attendance> attendances = attendanceRepository.findAllByIdStudentAndIdSubject(dto.getIdUser(), dto.getIdSubject());

        ArrayList<AttendanceDTO.DateForm> timeList = new ArrayList<AttendanceDTO.DateForm>();

        for (Attendance attendance: attendances) {
            String[] times = attendance.getDateAttendance().split("-");
            timeList.add(
                    AttendanceDTO.DateForm.builder()
                            .year(times[0])
                            .month(times[1])
                            .day(times[2])
                            .week(attendance.getWeekAttendance())
                            .time(attendance.getTimeAttendance())
                            .build()
            );
        }

        return timeList;
    }
}
