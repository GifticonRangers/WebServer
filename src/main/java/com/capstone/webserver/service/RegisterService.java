package com.capstone.webserver.service;

import com.capstone.webserver.api.UserController;
import com.capstone.webserver.dto.UserForm;
import com.capstone.webserver.entity.User;
import com.capstone.webserver.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class RegisterService {
    @Autowired
    UserRepository userRepository;

    public User register(UserForm dto) {
        User user = dto.toEntity();
        if(user != null) {
            userRepository.save(user);
            log.info("User: {}", user.toString());
            return user;
        }
        else {
            log.error("Invalid request: type Error");
            return null;
        }
    }
}
