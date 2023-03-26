package com.capstone.webserver.repository;

import com.capstone.webserver.entity.attendance.Attendance;
import org.springframework.data.repository.CrudRepository;

import java.lang.reflect.Array;
import java.util.ArrayList;

public interface AttendanceRepository extends CrudRepository<Attendance, Long> {

    /* 모든 출석 반환 */
    ArrayList<Attendance> findAll();

    /* 특정 과목, 특정 주차의 차시 정보를 반환 */
    ArrayList<Attendance> findAllByWeekAttendanceAndTimeAttendanceAndIdSubject(String weekAttendance, String timeAttendance, Long idSubject);

    /* 유저의 특정 과목 출석 정보를 반환 */
    ArrayList<Attendance> findAllByIdStudentAndIdSubject(Long idStudent, Long idSubject);
    
    /* 유저의 출석 정보 반환 */
    ArrayList<Attendance> findAllByIdStudent(Long idStudent);
}
