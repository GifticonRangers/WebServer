package com.capstone.webserver.service.user;

import com.capstone.webserver.entity.user.Role;
import com.capstone.webserver.entity.user.User;
import com.capstone.webserver.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
@Slf4j
public class UserService {
    @Autowired
    UserRepository userRepository;

    /* 모든 유저 반환 */
    public ArrayList<User> showAllUser() {
        log.info("Request show: All");
        return userRepository.findAll();
    }

    /* 타입별 유저 반환 */
    public ArrayList<User> showAllUserByTypeUser(String type) {
        log.info("Request show: {}", type);
        Role role;

        switch (type){
            case "ADMIN":
                role = Role.ADMIN;
                break;
            case "PROFESSOR":
                role = Role.PROFESSOR;
                break;
            case "STUDENT":
                role = Role.STUDENT;
                break;
            default:
                log.error("Invalid request: Not found type");
                return null;
        }

        return userRepository.findAllByTypeUser(role);
    }

    /* id에 따른 유저 반환 */
    public User showUserByIdUser(String id) {
        User user = userRepository.findByIdUser(id).orElse(null);

        if(user != null)
            log.info("Request show: {}", user.toString());
        else
            log.error("Invalid request: Not found id");

        return user;
    }
}
