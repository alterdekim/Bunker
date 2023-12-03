package com.alterdekim.javabot.service;

import com.alterdekim.javabot.entities.AdditionalFacts;

import java.util.List;

public interface AdditionalFactsService {
    List<AdditionalFacts> getAllFacts();
    AdditionalFacts getAdditionalFactById(long addFactId);
}
