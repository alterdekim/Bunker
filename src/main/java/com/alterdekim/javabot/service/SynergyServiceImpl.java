package com.alterdekim.javabot.service;

import com.alterdekim.javabot.entities.Synergy;
import com.alterdekim.javabot.repository.SynergyRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SynergyServiceImpl implements SynergyService {

    private final SynergyRepository repository;

    public SynergyServiceImpl(SynergyRepository repository) {
        this.repository = repository;
    }

    @Override
    public void removeById(long synergyId) {
        repository.deleteById(synergyId);
    }

    @Override
    public void saveSynergy(Synergy synergy) {
        repository.save(synergy);
    }

    @Override
    public List<Synergy> getAllSynergies() {
        return repository.findAll();
    }
}
