package com.alterdekim.javabot.service;

import com.alterdekim.javabot.entities.Disaster;

import java.util.List;

public interface DisasterService {
    List<Disaster> getAllDisasters();
    Disaster getDisasterById(long dId);

    void removeById(long dId);

    void saveDisaster(Disaster disaster);
}
