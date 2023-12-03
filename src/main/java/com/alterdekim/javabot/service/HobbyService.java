package com.alterdekim.javabot.service;

import com.alterdekim.javabot.entities.Hobby;

import java.util.List;

public interface HobbyService {
    List<Hobby> getAllHobbies();
    Hobby getHobbyById(long hobbyId);

    void saveHobby(Hobby hobby);

    void removeById(Long id);
}
