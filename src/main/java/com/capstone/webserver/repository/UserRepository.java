package com.capstone.webserver.repository;

import com.capstone.webserver.entity.user.Role;
import com.capstone.webserver.entity.user.User;
import org.springframework.data.repository.CrudRepository;

import java.util.ArrayList;
import java.util.Optional;

public interface UserRepository extends CrudRepository<User, Long> {

    /* 모든 유저 반환 */
    ArrayList<User> findAll();

    /* 특정 타입 유저 반환 */
    ArrayList<User> findAllByTypeUser(Role type);

    /* 해당 IdUser 유저 반환 */
    Optional<User> findByIdUser(String idUser);

    /* 해당 아이디와 역할 찾는 것 */
    Optional<User> findByIdAndTypeUser(Long id, Role typeUser);
}
