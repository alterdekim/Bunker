package com.alterdekim.javabot.service;

import com.alterdekim.javabot.entities.AdditionalFacts;
import com.alterdekim.javabot.repository.AdditionalFactsRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdditionalFactsServiceImpl implements AdditionalFactsService {

    private AdditionalFactsRepository repository;

    public AdditionalFactsServiceImpl(AdditionalFactsRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<AdditionalFacts> getAllFacts() {
        return repository.findAll();
    }

    @Override
    public AdditionalFacts getAdditionalFactById(long addFactId) {
        return repository.findById(addFactId).orElse(null);
    }
}