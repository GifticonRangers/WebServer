package com.capstone.webserver.service;

import com.capstone.webserver.entity.Role;
import com.capstone.webserver.entity.User;
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
    public ArrayList<User> findAllUser() {
        log.info("Request show: All");
        return userRepository.findAll();
    }

    /* 타입별 유저 반환 */
    public ArrayList<User> findAllUserByTypeUser(String type) {
        log.info("Request show: {}", type);
        Role role;

        switch (type){
            case "admin":
                role = Role.ADMIN;
                break;
            case "professor":
                role = Role.PROFESSOR;
                break;
            case "student":
                role = Role.STUDENT;
                break;
            default:
                log.error("Invalid request: Not found type");
                return null;
        }

        return userRepository.findAllByTypeUser(role);
    }

    /* id에 따른 유저 반환 */
    public User findUserById(Long id) {
        User user = userRepository.findById(id).orElse(null);

        if(user != null)
            log.info("Request show: {}", user.toString());
        else
            log.error("Invalid request: Not found id");

        return user;
    }

    /* id 중복 체크 */
    public boolean checkDuplicateId(String id) {
        return userRepository.findByIdUser(id) == null;
    }
}
