package com.capstone.webserver.service.user;

import com.capstone.webserver.config.error.CustomException;
import com.capstone.webserver.dto.UserDTO;
import com.capstone.webserver.entity.user.Auditor;
import com.capstone.webserver.repository.AuditorRepository;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.concurrent.CircuitBreakingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static com.capstone.webserver.config.error.ErrorCode.*;

@Slf4j
@Service
public class AuditorService {
    @Autowired
    AuditorRepository auditorRepository;

    public Auditor create(UserDTO.UserSubjectInfoForm dto) {
        if (dto.getIdUser() == null || dto.getIdSubject() == null)
            throw new CustomException(BadRequest);

        Auditor auditor = dto.toEntity();
        if(auditor == null)
            throw new CustomException(SERVER_ERROR);

        auditorRepository.save(auditor);
        log.info("Auditor: {}", auditor.toString());
        return auditor;
    }

}
