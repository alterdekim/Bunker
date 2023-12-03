package com.alterdekim.javabot.service;

import com.alterdekim.javabot.dto.UserDTO;
import com.alterdekim.javabot.entities.User;

import java.util.List;

public interface UserService {
    void saveUser(UserDTO userDto);

    User findByUsername(String usernane);

    List<UserDTO> findAllUsers();
}
