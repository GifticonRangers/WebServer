package com.capstone.webserver.repository;


import com.capstone.webserver.entity.user.Auditor;
import com.capstone.webserver.entity.user.User;
import org.springframework.data.repository.CrudRepository;

import java.util.ArrayList;

public interface AuditorRepository extends CrudRepository<Auditor, Long> {
    /* 모든 유저 반환 */
    ArrayList<Auditor> findAll();

    /* 해당 강좌를 듣는 유저 반환 */
    ArrayList<Auditor> findAllByIdSubject(Long idSubject);

    /* 특정 유저가 듣는 강좌 반환 */
    ArrayList<Auditor> findAllByIdUser(Long idUser);
}
