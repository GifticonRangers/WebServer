package com.capstone.webserver.repository;

import com.capstone.webserver.entity.attendance.Attendance;
import com.capstone.webserver.entity.attendance.State;
import org.springframework.data.repository.CrudRepository;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public interface AttendanceRepository extends CrudRepository<Attendance, Long> {

    /* 모든 출석 반환 */
    ArrayList<Attendance> findAll();

    /* 특정 과목, 특정 주차의 차시 정보를 반환 */
    ArrayList<Attendance> findAllByWeekAttendanceAndTimeAttendanceAndIdSubject(String weekAttendance, String timeAttendance, Long idSubject);

    /* 특정 과목, 특정 주차의 차시의 어떤 학생이 듣는 정보를 반환 */
    Attendance findByWeekAttendanceAndTimeAttendanceAndIdSubjectAndIdStudent(String weekAttendance, String timeAttendance, Long idSubject, Long idStudent);

    /* 유저의 특정 과목 출석 정보를 반환 */
    ArrayList<Attendance> findAllByIdStudentAndIdSubject(Long idStudent, Long idSubject);
    
    /* 유저의 출석 정보 반환 */
    ArrayList<Attendance> findAllByIdStudent(Long idStudent);

    List<Attendance> findByStateAttendance(State state);
}
