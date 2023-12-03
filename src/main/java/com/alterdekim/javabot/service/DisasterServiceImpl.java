package com.alterdekim.javabot.service;

import com.alterdekim.javabot.entities.Disaster;
import com.alterdekim.javabot.repository.DisasterRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DisasterServiceImpl implements DisasterService {

    private final DisasterRepository repository;

    public DisasterServiceImpl(DisasterRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<Disaster> getAllDisasters() {
        return repository.findAll();
    }

    @Override
    public Disaster getDisasterById(long dId) {
        return repository.findById(dId).orElse(null);
    }

    @Override
    public void removeById(long dId) {
        repository.deleteById(dId);
    }

    @Override
    public void saveDisaster(Disaster disaster) {
        repository.save(disaster);
    }
}
