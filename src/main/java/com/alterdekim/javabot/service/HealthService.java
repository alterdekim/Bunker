package com.alterdekim.javabot.service;

import com.alterdekim.javabot.entities.Health;
import com.alterdekim.javabot.entities.Synergy;

import java.util.List;

public interface HealthService {
    List<Health> getAllHealth();
    Health getHealthById(long healthId);

    void removeById(long healthId);

    void saveHealth(Health health);

    List<Synergy> getSynergies(Long id);
}
