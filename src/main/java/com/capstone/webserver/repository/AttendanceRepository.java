package com.capstone.webserver.repository;

import com.capstone.webserver.entity.attendance.Attendance;
import org.springframework.data.repository.CrudRepository;

import java.util.ArrayList;

public interface AttendanceRepository extends CrudRepository<Attendance, Long> {

    /* 모든 출석 반환 */
    ArrayList<Attendance> findAll();
}
