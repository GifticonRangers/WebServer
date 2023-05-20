package com.capstone.webserver.service.nfc;

import com.capstone.webserver.config.error.CustomException;
import com.capstone.webserver.dto.AttendanceDTO;
import com.capstone.webserver.entity.attendance.Attendance;
import com.capstone.webserver.entity.attendance.State;
import com.capstone.webserver.entity.nfc.Nfc;
import com.capstone.webserver.entity.user.User;
import com.capstone.webserver.repository.AttendanceRepository;
import com.capstone.webserver.repository.NFCRepository;
import com.capstone.webserver.repository.UserRepository;
import com.capstone.webserver.service.user.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.BodyInserter;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import reactor.netty.http.client.HttpClient;

import java.security.Principal;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Date;
import java.util.Map;
import java.util.Optional;

import static com.capstone.webserver.config.error.ErrorCode.BadRequest;
import static com.capstone.webserver.config.error.ErrorCode.USER_NOT_FOUND;

@Service
@Slf4j
@RequiredArgsConstructor
public class NfcService {

    @Autowired
    AttendanceRepository attendanceRepository;

    @Autowired
    NFCRepository nfcRepository;

    @Autowired
    UserService userService;

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
            attendance.setEndAttendance(null);
            attendance.setStateAttendance(State.ABSENCE);
            attendanceRepository.save(attendance);
        }

        /*
            인공지능 서버 호출해서 녹화하는 거 시작하는 코드 써야함 ㅋㅋ
            api호출 끝!
        */
        HttpClient httpClient = HttpClient.create()
                .responseTimeout(Duration.ofSeconds(2));

        WebClient webClient =
                WebClient
                        .builder()
                        .clientConnector(new ReactorClientHttpConnector(httpClient))
                        .baseUrl("http://210.111.178.30:9000")
                        .build();

        webClient
                        .get()
                        .uri(uriBuilder ->
                                uriBuilder
                                        .path("/record_start/" + week + "/" + time + "/" + idSubject)
                                        .build())
                        .retrieve()
                        .bodyToMono(String.class)
                        .subscribe(response -> {
                            log.info("영상녹화 시작");
                        }, error -> {
                            log.info("영상녹화 시작");
                        });;


//        return webClient
//                .get()
//                .uri(uriBuilder ->
//                        uriBuilder
//                                .path("/record_start/" + week + "/" + time + "/" + idSubject)
//                                .build())
//                .exchange()
//                .flatMap(response -> {
//                    log.info(response);
//                    if ("START_RECORDING".equals(response)) {
//                        return Mono.just("Recording started successfully");
//                    } else {
//                        return Mono.just("Failed to start recording");
//                    }
//                });

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

        ArrayList<Nfc> nfcArrayList = nfcRepository.findAll();
        MultiValueMap<String, String> formData = new LinkedMultiValueMap<>();

        for(Nfc nfc: nfcArrayList) {
            formData.add("pos/" + nfc.getId(), nfc.getNfcRow() + "/" + nfc.getNfcCol());
        }

        WebClient webClient =
                WebClient
                        .builder()
                        .baseUrl("http://210.111.178.30:9000")
                        .build();

        String response =
                webClient
                        .post()
                        .uri(uriBuilder ->
                                uriBuilder
                                        .path("/record_stop/" + week + "/" + time + "/" + idSubject)
                                        .build())
                        .body(BodyInserters.fromFormData(formData))
                        .retrieve()
                        .bodyToMono(String.class)
                        .block();

        // 결과 확인
        log.info(response);
    }

    public boolean authNfc(Principal principal, AttendanceDTO.NfcAttendanceForm dto) {
        User user = getAuth(principal);
        Long id = user.getId();
        /*
        * 프론트측에서 주는 nfc 주기적으로 읽었다는 data -> nfcCount++;
        * attendance.setNfcCount(nfcCount);
        * attendanceRepository.save(attendance);
        *
        * 주기적으로 읽어야하기때문에 -> httpState: 201 -> true
        *                          -> httpState: 200 -> false
        *                               => 참고로 endNfcTagApi가 동작하면 종료의 200을 보내야 함
        */

        String week = dto.getWeekAttendance();
        String time = dto.getTimeAttendance();
        Long idSubject = dto.getIdSubject();
        String nfcNumber = dto.getNfcNumber();


        if (week == null || time == null || idSubject == null) {
            log.error("Error: Not found data");
            throw new CustomException(BadRequest);
        }

        if (nfcRepository.findByNfcNumber(nfcNumber) == null) {
            log.error("Error: Not found nfcTag Info");
            throw new CustomException(BadRequest);
        }

        Attendance attendance = attendanceRepository
                .findByWeekAttendanceAndTimeAttendanceAndIdSubjectAndIdStudent(week, time, idSubject, id);

        if(attendance.getEndAttendance() == null){
            String[] startTime = attendance.getStartAttendance().split("/");
            int startH = Integer.parseInt(startTime[0]);
            int startM = Integer.parseInt(startTime[1]);

            Date nowDate = new Date();
            SimpleDateFormat sdf = new SimpleDateFormat("HH/mm");
            String[] nowTime = sdf.format(nowDate).split("/");

            int nowH = Integer.parseInt(nowTime[0]);
            int nowM = Integer.parseInt(nowTime[1]);

            if((nowH * 60 + nowM) - (startH * 60 + startM) >= 10)
                attendance.setStateAttendance(State.LATE);
            else
                attendance.setStateAttendance(State.ATTENDANCE);

            attendanceRepository.save(attendance);
            return true;
        }
        else
            return false;
    }

    public User getAuth(Principal principal) {
        String idUser = principal.getName();
        return userService.getUserByIdUser(idUser);
    }
}
