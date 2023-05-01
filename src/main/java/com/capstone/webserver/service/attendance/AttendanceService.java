package com.capstone.webserver.service.attendance;

import com.capstone.webserver.config.error.CustomException;
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

import static com.capstone.webserver.config.error.ErrorCode.*;

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

    public ArrayList<Attendance> createAttendanceList(UserDTO.UserSubjectInfoForm dto) {
        Long subjectId = dto.getIdSubject();
        Long professorId = dto.getIdUser();

        if (professorId == null || subjectId == null)
            throw new CustomException(BadRequest);

        Subject target = subjectRepository.findById(subjectId).orElseThrow(() -> new CustomException(SUBJECT_NOT_FOUND));
        ArrayList<Attendance> attendanceArrayList = new ArrayList<Attendance>();

        Map<Integer, ArrayList<String>> schedules = DateUtil.computeDate(
                Integer.parseInt(target.getYearSubject()),
                target.getSemesterSubject(),
                target.getTimeSubject()
        );
        ArrayList<Auditor> auditors = auditorRepository.findAllByIdSubject(subjectId);

        if(auditors == null || auditors.isEmpty()) {
            throw new CustomException(AUDITOR_NOT_FOUND);
        }

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
                            .startAttendance(null)
                            .endAttendance(null)
                            .nfcCount(0)
                            .build();

                    if (entity == null)
                        throw new CustomException(SERVER_ERROR);

                    log.info("Attendance: {}", entity.toString());
                    Attendance save = attendanceRepository.save(entity);
                    attendanceArrayList.add(save);

                    j++;
                }
            }
        }

        return attendanceArrayList;
    }

    public Attendance updateAttendance(AttendanceDTO.updateAttendanceForm dto) {
        if (dto.getId() == null || (dto.getStateAttendance() < 0 || dto.getStateAttendance() > 3))
            throw new CustomException(BadRequest);

        Attendance attendance = dto.toEntity();

        if (attendance == null)
            throw new CustomException(SERVER_ERROR);

        Attendance target = attendanceRepository.findById(attendance.getId()).orElseThrow(() -> new CustomException(ATTENDANCE_NOT_FOUND));

        target.patch(attendance);

        return attendanceRepository.save(target);
    }

    public ArrayList<Attendance> showAttendanceByTime(AttendanceDTO.showAttendanceForm dto) {
        String week = dto.getWeekAttendance();
        String time = dto.getTimeAttendance();
        Long idSubject = dto.getIdSubject();

        if (week == null || time == null || idSubject == null) {
            log.error("Error: Not found data");
            throw new CustomException(BadRequest);
        }

        ArrayList<Attendance> attendances = new ArrayList<Attendance>();

        ArrayList<Attendance> target = attendanceRepository
                                        .findAllByWeekAttendanceAndTimeAttendanceAndIdSubject(week, time, idSubject);

        if (target == null || target.isEmpty()){
            throw new CustomException(ATTENDANCE_NOT_FOUND);
        }

        for (Attendance attendance: target)
            if (userRepository
                    .findById(attendance.getIdStudent())
                    .orElseThrow(() -> new CustomException(ATTENDANCE_NOT_FOUND))
                    .getTypeUser() == Role.STUDENT)
                attendances.add(attendance);


        return attendances;
    }

    public ArrayList<Attendance> showAttendanceByUser(AttendanceDTO.checkAttendanceForm dto) {
        Long idStudent = dto.getIdStudent();
        Long idSubject = dto.getIdSubject();

        if (idStudent == null || idSubject == null) {
            log.error("Error: Not found data");
            throw new CustomException(BadRequest);
        }
        ArrayList<Attendance> attendances = attendanceRepository.findAllByIdStudentAndIdSubject(idStudent, idSubject);

        if (attendances == null || attendances.isEmpty()){
            throw new CustomException(ATTENDANCE_NOT_FOUND);
        }

        return attendances;
    }

    public AttendanceDTO.AttendanceInfoForm showAttendanceInfo(AttendanceDTO.showAttendanceForm dto) {
        String week = dto.getWeekAttendance();
        String time = dto.getTimeAttendance();
        Long idSubject = dto.getIdSubject();

        if (week == null || time == null || idSubject == null) {
            log.error("Error: Not found data");
            throw new CustomException(BadRequest);
        }

        ArrayList<Attendance> attendances = attendanceRepository.findAllByWeekAttendanceAndTimeAttendanceAndIdSubject(week, time, idSubject);

        if (attendances == null || attendances.isEmpty()) {
            throw new CustomException(ATTENDANCE_NOT_FOUND);
        }

        AttendanceDTO.AttendanceInfoForm attendanceInfoForm = AttendanceDTO
                .AttendanceInfoForm
                .builder()
                .ATTENDANCE(0)
                .LATE(0)
                .ABSENCE(0)
                .PUBLIC_ABSENCE(0)
                .build();

        for (Attendance attendance: attendances) {
            User user = userRepository
                    .findById(attendance.getIdStudent())
                    .orElseThrow(() -> new CustomException(USER_NOT_FOUND));

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
        Long idUser = dto.getIdUser();
        Long idSubject = dto.getIdSubject();

        if (idUser == null || idSubject == null) {
            log.error("Error: Not found data");
            throw new CustomException(BadRequest);
        }

        ArrayList<Attendance> attendances = attendanceRepository.findAllByIdStudentAndIdSubject(idUser, idSubject);

        if (attendances == null || attendances.isEmpty()) {
            throw new CustomException(ATTENDANCE_NOT_FOUND);
        }

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
