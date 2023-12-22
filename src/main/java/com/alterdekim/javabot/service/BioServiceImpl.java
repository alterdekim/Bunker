package com.alterdekim.javabot.service;

import com.alterdekim.javabot.entities.Bio;
import com.alterdekim.javabot.entities.Synergy;
import com.alterdekim.javabot.repository.BioRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BioServiceImpl implements BioService {

    private BioRepository repository;

    public BioServiceImpl(BioRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<Bio> getAllBios() {
        return repository.findAll();
    }

    @Override
    public Bio getBioById(long bioId) {
        return repository.findById(bioId).orElse(null);
    }

    @Override
    public void saveBio(Bio bio) {
        repository.save(bio);
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
