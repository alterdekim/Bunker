package com.alterdekim.javabot.service;

import com.alterdekim.javabot.entities.Bio;

import java.util.List;

public interface BioService {
    List<Bio> getAllBios();
    Bio getBioById(long bioId);

    void saveBio(Bio bio);

    void removeById(Long id);
}
