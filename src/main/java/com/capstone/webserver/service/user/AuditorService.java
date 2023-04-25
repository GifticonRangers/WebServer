package com.capstone.webserver.service.user;

import com.capstone.webserver.config.error.CustomException;
import com.capstone.webserver.dto.UserDTO;
import com.capstone.webserver.entity.user.Auditor;
import com.capstone.webserver.repository.AuditorRepository;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.concurrent.CircuitBreakingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

import static com.capstone.webserver.config.error.ErrorCode.*;

@Slf4j
@Service
public class AuditorService {
    @Autowired
    AuditorRepository auditorRepository;

    public ArrayList<Auditor> addSubject(UserDTO.AddSubjectForm dto) {
        Long idUser = dto.getIdUser();
        ArrayList<Long> addSubjectList = dto.getSubjects();

        if (idUser == null || addSubjectList == null)
            throw new CustomException(BadRequest);

        ArrayList<Auditor> auditors = new ArrayList<>();

        for (int i = 0; i < addSubjectList.size(); i++) {

            Long idSubject = dto.getSubjects().get(i);
            log.info(String.valueOf(idSubject));

            Auditor auditor = Auditor.builder()
                    .idUser(idUser)
                    .idSubject(idSubject)
                    .build();

            auditorRepository.save(auditor);
            log.info("Auditor: {}", auditor);
            auditors.add(auditor);
        }

        if (auditors == null)
            throw new CustomException(SERVER_ERROR);

        return auditors;
    }
}
