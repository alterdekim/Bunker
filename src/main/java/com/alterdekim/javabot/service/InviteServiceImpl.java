package com.alterdekim.javabot.service;

import com.alterdekim.javabot.entities.Invite;
import com.alterdekim.javabot.repository.InviteRepository;
import org.springframework.stereotype.Service;

@Service
public class InviteServiceImpl implements InviteService {

    private final InviteRepository repository;

    public InviteServiceImpl(InviteRepository repository) {
        this.repository = repository;
    }

    @Override
    public Invite findById(Integer id) {
        return repository.findById(id).orElse(null);
    }
}
