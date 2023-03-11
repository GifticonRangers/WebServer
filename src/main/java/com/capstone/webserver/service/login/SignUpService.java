package com.capstone.webserver.service.login;

import com.capstone.webserver.dto.user.UserDTO;
import com.capstone.webserver.entity.user.User;
import com.capstone.webserver.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class SignUpService {
    @Autowired
    UserRepository userRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    /* SignUp Service */
    public User signup(UserDTO.UserForm dto) {
        User user = dto.toEntity();
        if(user != null) {
            user.setPwUser(passwordEncoder.encode(user.getPwUser()));
            userRepository.save(user);
            log.info("User: {}", user.toString());
            return user;
        }
        else {
            log.error("Invalid request: type Error");
            return null;
        }
    }

    /* id 중복 체크 */
    public Boolean checkDuplicateId(String id) {
        return userRepository.findByIdUser(id) == null;
    }
}
