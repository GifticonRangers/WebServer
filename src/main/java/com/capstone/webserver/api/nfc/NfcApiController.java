package com.capstone.webserver.api.nfc;

import com.capstone.webserver.dto.AttendanceDTO;
import com.capstone.webserver.entity.user.User;
import com.capstone.webserver.service.nfc.NfcService;
import com.capstone.webserver.service.user.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@Slf4j
@Tag(name = "nfc", description = "NFC API")
public class NfcApiController {

    @Autowired
    NfcService nfcService;

    @Operation(summary = "start NFC tag by Professor",
            description = "NFC 출석 시작\n\n"
                        + "weekAttendance(출석 주차), timeAttendance(출석 차시), idSubject(과목 기본키 값) 필요")
    @PostMapping("/api/nfc/startNfcTag")
    public ResponseEntity startNfcTag(@RequestBody AttendanceDTO.showAttendanceForm dto) {
        nfcService.startNfcTag(dto);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @Operation(summary = "end NFC tag by Professor",
            description = "NFC 출석 마감\n\n"
                        + "weekAttendance(출석 주차), timeAttendance(출석 차시), idSubject(과목 기본키 값) 필요")
    @PostMapping("/api/nfc/endNfcTag")
    public ResponseEntity endNfcTag(@RequestBody AttendanceDTO.showAttendanceForm dto) {
        nfcService.endNfcTag(dto);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @Operation(summary = "auth NFC tag by Student",
            description = "NFC 인증\n\n"
                        + "weekAttendance(출석 주차), timeAttendance(출석 차시), idSubject(과목 기본키 값) 필요")
    @PostMapping("/api/nfc/authNFC")
    public ResponseEntity authNfc(Principal principal, @RequestBody AttendanceDTO.showAttendanceForm dto) {
        boolean state = nfcService.authNfc(principal, dto);

        /*
        * state = true 201 반환
        * state = false 200 반환
        * */
        return state ? ResponseEntity.status(HttpStatus.CREATED).build() : ResponseEntity.status(HttpStatus.OK).build();
    }
}
