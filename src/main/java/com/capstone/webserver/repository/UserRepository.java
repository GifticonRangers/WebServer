package com.capstone.webserver.repository;

import com.capstone.webserver.entity.Role;
import com.capstone.webserver.entity.User;
import org.springframework.data.repository.CrudRepository;

import java.util.ArrayList;

public interface UserRepository extends CrudRepository<User, Long> {
    ArrayList<User> findAll();

    ArrayList<User> findAllByTypeUser(Role type);
}
