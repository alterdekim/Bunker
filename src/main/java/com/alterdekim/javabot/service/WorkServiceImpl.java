package com.alterdekim.javabot.service;

import com.alterdekim.javabot.entities.Synergy;
import com.alterdekim.javabot.entities.Work;
import com.alterdekim.javabot.repository.WorkRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WorkServiceImpl implements WorkService {

    private final WorkRepository repository;

    public WorkServiceImpl(WorkRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<Work> getAllWorks() {
        return repository.findAll();
    }

    @Override
    public Work getWorkById(long workId) {
        return repository.findById(workId).orElse(null);
    }

    @Override
    public void removeById(long workId) {
        repository.deleteById(workId);
    }

    @Override
    public void saveWork(Work work) {
        repository.save(work);
    }

    @Override
    public List<Synergy> getSynergies(Long id) {
        return repository.getSynergies(id);
    }
}
