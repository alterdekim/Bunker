package com.alterdekim.javabot.service;

import com.alterdekim.javabot.entities.Synergy;
import com.alterdekim.javabot.entities.Work;

import java.util.List;

public interface WorkService {
    List<Work> getAllWorks();
    Work getWorkById(long workId);

    void removeById(long workId);

    void saveWork(Work work);

    List<Synergy> getSynergies(Long id);
}