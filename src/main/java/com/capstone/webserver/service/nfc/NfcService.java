package com.capstone.webserver.service.nfc;

import com.capstone.webserver.config.error.CustomException;
import com.capstone.webserver.dto.AttendanceDTO;
import com.capstone.webserver.entity.attendance.Attendance;
import com.capstone.webserver.repository.AttendanceRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import static com.capstone.webserver.config.error.ErrorCode.BadRequest;

@Service
@Slf4j
@RequiredArgsConstructor
public class NfcService {

    @Autowired
    AttendanceRepository attendanceRepository;

    public void startNfcTag(AttendanceDTO.showAttendanceForm dto) {
        String week = dto.getWeekAttendance();
        String time = dto.getTimeAttendance();
        Long idSubject = dto.getIdSubject();


        if (week == null || time == null || idSubject == null) {
            log.error("Error: Not found data");
            throw new CustomException(BadRequest);
        }

        Date nowDate = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("HH/mm");
        String startTime = sdf.format(nowDate);

        ArrayList<Attendance> target = attendanceRepository
                .findAllByWeekAttendanceAndTimeAttendanceAndIdSubject(week, time, idSubject);

        for (Attendance attendance: target) {
            attendance.setStartAttendance(startTime);
            attendance.setNfcCount(0);
            attendanceRepository.save(attendance);
        }

        /*
            인공지능 서버 호출해서 녹화하는 거 시작하는 코드 써야함 ㅋㅋ
            api호출 끝!
         */
    }

    public void endNfcTag(AttendanceDTO.showAttendanceForm dto) {
        String week = dto.getWeekAttendance();
        String time = dto.getTimeAttendance();
        Long idSubject = dto.getIdSubject();


        if (week == null || time == null || idSubject == null) {
            log.error("Error: Not found data");
            throw new CustomException(BadRequest);
        }

        Date nowDate = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("HH/mm");
        String endTime = sdf.format(nowDate);

        ArrayList<Attendance> target = attendanceRepository
                .findAllByWeekAttendanceAndTimeAttendanceAndIdSubject(week, time, idSubject);

        for (Attendance attendance: target) {
            attendance.setEndAttendance(endTime);
            attendanceRepository.save(attendance);
        }

        /*
            인공지능 서버 호출해서 녹화하는 거 중지하는 코드 써야함 ㅋㅋ
            api호출 끝!
         */
    }

    public boolean authNfc(Long studentId, AttendanceDTO.showAttendanceForm dto) {
        /*
        * 프론트측에서 주는 nfc 주기적으로 읽었다는 data -> nfcCount++;
        * attendance.setNfcCount(nfcCount);
        * attendanceRepository.save(attendance);
        *
        * 주기적으로 읽어야하기때문에 -> httpState: 2xx(계속 nfc 읽고 있다는 의미의 200) -> true
        *                          -> httpState: 2xx(종료될 때의 200)                -> false
        *                               => 참고로 endNfcTagApi가 동작하면 종료의 200을 보내야 함
        */

        String week = dto.getWeekAttendance();
        String time = dto.getTimeAttendance();
        Long idSubject = dto.getIdSubject();


        if (week == null || time == null || idSubject == null) {
            log.error("Error: Not found data");
            throw new CustomException(BadRequest);
        }

        Attendance attendance = attendanceRepository
                .findByWeekAttendanceAndTimeAttendanceAndIdSubjectAndIdStudent(week, time, idSubject, studentId);

        if(attendance.getEndAttendance() == null){
            int count = attendance.getNfcCount();
            attendance.setNfcCount(count+1);
            attendanceRepository.save(attendance);
            return true;
        }
        else
            return false;
    }
}
