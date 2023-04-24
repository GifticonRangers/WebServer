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

import java.security.Principal;
import java.util.ArrayList;
import java.util.Optional;

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
    public ArrayList<User> showAllUserByTypeUser(UserDTO.typeUserForm dto) {
        if (dto.getType() < 0 || dto.getType() > 2)
            throw new CustomException(BadRequest);

        String type = Role.values()[dto.getType()].toString();


        log.info("Request show: {}", type);
        Role role = null;

        switch (type) {
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
                throw new CustomException(SERVER_ERROR);
        }

        ArrayList<User> users = userRepository.findAllByTypeUser(role);

        if (users == null || users.isEmpty())
            throw new CustomException(USER_NOT_FOUND);

        return users;
    }

    /* id에 따른 유저 반환 */
    public User showUserById(UserDTO.userIdForm dto) {
        Long id = dto.getId();

        if (id == null)
            throw new CustomException(BadRequest);

        User user = userRepository
                        .findById(id)
                        .orElseThrow(() -> new CustomException(USER_NOT_FOUND));

        log.info("Request show: {}", user.toString());

        return user;
    }

    public ArrayList<User> showUserBySubjectId(SubjectDTO.SubjectIdForm dto) {
        Long id = dto.getId();

        if (id == null) {
            log.error("Error: Not found id");
            throw new CustomException(BadRequest);
        }

        ArrayList<Auditor> auditors = auditorRepository.findAllByIdSubject(id);

        if (auditors == null || auditors.isEmpty())
            throw new CustomException(AUDITOR_NOT_FOUND);

        ArrayList<User> users = new ArrayList<User>();

        for (Auditor auditor: auditors) {
            Long idUser = auditor.getIdUser();
            if (idUser == null)
                throw new CustomException(AUDITOR_NOT_FOUND);

            User user = userRepository
                            .findById(idUser)
                            .orElseThrow(() -> new CustomException(USER_NOT_FOUND));

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
        ArrayList<Attendance> attendanceArrayList = attendanceRepository
                .findAllByWeekAttendanceAndTimeAttendanceAndIdSubject(week, time, idSubject);

        if (attendanceArrayList == null || attendanceArrayList.isEmpty())
            throw new CustomException(ATTENDANCE_NOT_FOUND);

        for (Attendance attendance: attendanceArrayList) {
            Long idStudent = attendance.getIdStudent();
            if (idStudent == null)
                throw new CustomException(SERVER_ERROR);
            
            User user = userRepository
                            .findById(idStudent)
                            .orElseThrow(() -> new CustomException(USER_NOT_FOUND));
            
            if (user.getTypeUser() == Role.STUDENT) {
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
        }

        if (userAttendanceForms.isEmpty())
            throw new CustomException(SERVER_ERROR);

        return userAttendanceForms;
    }

    public User hasUser(Principal principal) {
        User user = userRepository
                .findByIdUser(principal.getName())
                .orElseThrow(() -> new CustomException(USER_NOT_FOUND));

        return user;
    }
}
