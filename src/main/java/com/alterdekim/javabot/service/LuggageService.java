package com.alterdekim.javabot.service;

import com.alterdekim.javabot.entities.Luggage;

import java.util.List;

public interface LuggageService {
    List<Luggage> getAllLuggages();
    Luggage getLuggageById(long luggageId);

    void removeById(long luggageId);

    void saveLuggage(Luggage luggage);
}
