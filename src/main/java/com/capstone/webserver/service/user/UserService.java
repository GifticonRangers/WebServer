package com.capstone.webserver.service.user;

import com.capstone.webserver.config.error.CustomException;
import com.capstone.webserver.dto.AttendanceDTO;
import com.capstone.webserver.dto.SubjectDTO;
import com.capstone.webserver.dto.UserDTO;
import com.capstone.webserver.entity.attendance.Attendance;
import com.capstone.webserver.entity.user.Auditor;
import com.capstone.webserver.entity.user.Role;
import com.capstone.webserver.entity.user.User;
import com.capstone.webserver.repository.AttendanceRepository;
import com.capstone.webserver.repository.AuditorRepository;
import com.capstone.webserver.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

import static com.capstone.webserver.config.error.ErrorCode.*;

@Service
@Slf4j
public class UserService {
    @Autowired
    UserRepository userRepository;

    @Autowired
    AuditorRepository auditorRepository;

    @Autowired
    AttendanceRepository attendanceRepository;

    /* 모든 유저 반환 */
    public ArrayList<User> showAllUser() {
        log.info("Request show: All");

        if (userRepository == null)
            throw new CustomException(BadRequest);

        return userRepository.findAll();
    }

    /* 타입별 유저 반환 */
    public ArrayList<User> showAllUserByTypeUser(String type) {
        if (type == null || userRepository == null)
            throw new CustomException(BadRequest);

        log.info("Request show: {}", type);
        Role role;

        switch (type){
            case "ADMIN":
                role = Role.ADMIN;
                break;
            case "PROFESSOR":
                role = Role.PROFESSOR;
                break;
            case "STUDENT":
                role = Role.STUDENT;
                break;
            default:
                log.error("Invalid request: Not found type");
                return null;
        }

        return userRepository.findAllByTypeUser(role);
    }

    /* id에 따른 유저 반환 */
    public User showUserById(Long id) {
        User user = userRepository
                        .findById(id)
                        .orElseThrow(() -> new CustomException(USER_NOT_FOUND));

        if(user != null)
            log.info("Request show: {}", user.toString());
        else
            log.error("Invalid request: Not found id");

        return user;
    }

    public ArrayList<User> showUserBySubjectId(SubjectDTO.SubjectForm dto) {
        Long id = dto.getId();

        if (id == null) {
            log.error("Error: Not found id");
            throw new CustomException(SUBJECT_NOT_FOUND);
        }

        ArrayList<Auditor> auditors = auditorRepository.findAllByIdSubject(id);
        ArrayList<User> users = new ArrayList<User>();

        if (auditors == null || auditors.isEmpty())
            throw new CustomException(AUDITOR_NOT_FOUND);

        for (Auditor auditor: auditors) {
            User user = userRepository.findByIdAndTypeUser(auditor.getIdUser(), Role.STUDENT);
            if(user != null)
                users.add(user);
        }

        return users;
    }

    public ArrayList<UserDTO.UserAttendanceForm> showUserAttendanceBySubjectId(AttendanceDTO.showAttendanceForm dto) {
        String week = dto.getWeekAttendance();
        String time = dto.getTimeAttendance();
        Long idSubject = dto.getIdSubject();

        if (week == null || time == null || idSubject == null) {
            log.error("Error: Not found data");
            throw new CustomException(BadRequest);
        }

        ArrayList<UserDTO.UserAttendanceForm> userAttendanceForms = new ArrayList<UserDTO.UserAttendanceForm>();
        ArrayList<Attendance> attendanceArrayList = attendanceRepository.findAllByWeekAttendanceAndTimeAttendanceAndIdSubject(week, time, idSubject);

        if (attendanceArrayList == null || attendanceArrayList.isEmpty())
            throw new CustomException(ATTENDANCE_NOT_FOUND);

        for (Attendance attendance: attendanceArrayList)
            if (userRepository.findById(attendance.getIdStudent()).orElse(null).getTypeUser() == Role.STUDENT) {
                User user = userRepository.findById(attendance.getIdStudent()).orElse(null);
                userAttendanceForms.add
                        (
                                UserDTO.UserAttendanceForm
                                        .builder()
                                        .id(attendance.getIdStudent())
                                        .state(attendance.getStateAttendance())
                                        .idUser(user.getIdUser())
                                        .name(user.getNameUser())
                                        .build()
                        );
            }

        return userAttendanceForms;
    }
}
