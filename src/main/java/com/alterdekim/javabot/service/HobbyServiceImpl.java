package com.alterdekim.javabot.service;

import com.alterdekim.javabot.entities.Hobby;
import com.alterdekim.javabot.entities.Synergy;
import com.alterdekim.javabot.repository.HobbyRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HobbyServiceImpl implements HobbyService {

    private final HobbyRepository repository;

    public HobbyServiceImpl(HobbyRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<Hobby> getAllHobbies() {
        return repository.findAll();
    }

    @Override
    public Hobby getHobbyById(long hobbyId) {
        return repository.findById(hobbyId).orElse(null);
    }

    @Override
    public void saveHobby(Hobby hobby) {
        repository.save(hobby);
    }

    @Override
    public void removeById(Long id) {
        repository.deleteById(id);
    }

    @Override
    public List<Synergy> getSynergies(Long id) {
        return repository.getSynergies(id);
    }
}
