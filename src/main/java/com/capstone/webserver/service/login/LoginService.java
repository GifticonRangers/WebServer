package com.capstone.webserver.service.login;

import com.capstone.webserver.dto.UserDTO;
import com.capstone.webserver.entity.user.User;
import com.capstone.webserver.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class LoginService {
    @Autowired
    UserRepository userRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    /* Login Service */
    public User login(UserDTO.UserForm dto) {
        User user = dto.toEntity();
        log.info("User: {}", user.toString());
        User target = userRepository.findByIdUser(user.getIdUser());

        if (target == null) {
            log.error("Invalid request: Not found id");
            return null;
        }

        if (passwordEncoder.matches(user.getPwUser(), target.getPwUser())) {
            log.info("Login Success");
            return target;
        }
        else {
            log.error("Login Failed");
            return null;
        }
    }
}
