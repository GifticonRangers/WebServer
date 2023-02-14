package com.capstone.webserver.api.user;

import com.capstone.webserver.dto.user.AuditorForm;
import com.capstone.webserver.entity.user.Auditor;
import com.capstone.webserver.service.user.AuditorService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


@Slf4j
@RestController
public class AuditorApiController {
    @Autowired
    AuditorService auditorService;

    @PostMapping("/api/student/auditor/create")
    public ResponseEntity<Auditor> create(@RequestBody AuditorForm dto) {
        Auditor auditor = auditorService.create(dto);
        return ResponseEntity
                .status(auditor != null ? HttpStatus.OK : HttpStatus.BAD_REQUEST)
                .body(auditor);
    }
}
