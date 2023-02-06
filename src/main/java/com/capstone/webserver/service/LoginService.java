package com.capstone.webserver.service;

import com.capstone.webserver.dto.UserForm;
import com.capstone.webserver.entity.User;
import com.capstone.webserver.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@Slf4j
public class LoginService {
    @Autowired
    UserRepository userRepository;

    /* Login Service */
    public User login(UserForm dto) {
        User user = dto.toEntity();
        log.info("User: {}", user.toString());
        User target = userRepository.findByIdUser(user.getIdUser());

        if (target == null) {
            log.error("Invalid request: Not found id");
            return null;
        }

        if (Objects.equals(target.getPwUser(), user.getPwUser())) {
            log.info("Login Success");
            return target;
        }
        else {
            log.error("Login Failed");
            return null;
        }
    }
}
