package com.alterdekim.javabot.service;

import com.alterdekim.javabot.entities.Luggage;
import com.alterdekim.javabot.entities.Synergy;
import com.alterdekim.javabot.repository.LuggageRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LuggageServiceImpl implements LuggageService {

    private final LuggageRepository repository;

    public LuggageServiceImpl(LuggageRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<Luggage> getAllLuggages() {
        return repository.findAll();
    }

    @Override
    public Luggage getLuggageById(long luggageId) {
        return repository.findById(luggageId).orElse(null);
    }

    @Override
    public void removeById(long luggageId) {
        repository.deleteById(luggageId);
    }

    @Override
    public void saveLuggage(Luggage luggage) {
        repository.save(luggage);
    }

    @Override
    public List<Synergy> getSynergies(Long id) {
        return repository.getSynergies(id);
    }
}
