package com.alterdekim.javabot.service;

import com.alterdekim.javabot.entities.Health;
import com.alterdekim.javabot.entities.Synergy;
import com.alterdekim.javabot.repository.HealthRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HealthServiceImpl implements HealthService {

    private final HealthRepository repository;

    public HealthServiceImpl(HealthRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<Health> getAllHealth() {
        return repository.findAll();
    }

    @Override
    public Health getHealthById(long healthId) {
        return repository.findById(healthId).orElse(null);
    }

    @Override
    public void removeById(long healthId) {
        repository.deleteById(healthId);
    }

    @Override
    public void saveHealth(Health health) {
        repository.save(health);
    }

    @Override
    public List<Synergy> getSynergies(Long id) {
        return repository.getSynergies(id);
    }
}
