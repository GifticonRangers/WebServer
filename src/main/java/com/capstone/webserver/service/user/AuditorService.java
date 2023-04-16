package com.capstone.webserver.service.user;

import com.capstone.webserver.config.error.CustomException;
import com.capstone.webserver.dto.UserDTO;
import com.capstone.webserver.entity.user.Auditor;
import com.capstone.webserver.repository.AuditorRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static com.capstone.webserver.config.error.ErrorCode.*;

@Slf4j
@Service
public class AuditorService {
    @Autowired
    AuditorRepository auditorRepository;

    public Auditor create(UserDTO.UserSubjectInfoForm dto) {
        if (dto == null)
            throw new CustomException(BadRequest);

        Auditor auditor = dto.toEntity();
        auditorRepository.save(auditor);
        log.info("Auditor: {}", auditor.toString());
        return auditor;
    }

}
