package com.capstone.webserver.api.user;

import com.capstone.webserver.dto.UserDTO;
import com.capstone.webserver.entity.user.Auditor;
import com.capstone.webserver.service.user.AuditorService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


@Slf4j
@RestController
@Tag(name = "user", description = "auditor API")
public class AuditorApiController {
    @Autowired
    AuditorService auditorService;

    @Operation(summary = "Create Auditor",
               description = "수강생 생성\n\n"
                            + "idUser(유저 기본키값), idSubject(강좌 기본키값) 입력 필요")
    @PostMapping("/api/user/auditor/create")
    public ResponseEntity<Auditor> create(@RequestBody UserDTO.UserSubjectInfoForm dto) {
        Auditor auditor = auditorService.create(dto);
        return ResponseEntity
                .status(auditor != null ? HttpStatus.OK : HttpStatus.BAD_REQUEST)
                .body(auditor);
    }
}
