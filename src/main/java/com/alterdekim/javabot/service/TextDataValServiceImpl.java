package com.alterdekim.javabot.service;

import com.alterdekim.javabot.entities.TextDataVal;
import com.alterdekim.javabot.repository.TextDataValRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TextDataValServiceImpl implements TextDataValService {

    private final TextDataValRepository repository;

    public TextDataValServiceImpl(TextDataValRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<TextDataVal> getAllTexts() {
        return repository.findAll();
    }

    @Override
    public TextDataVal getTextDataValById(long textId) {
        return repository.findById(textId).orElse(null);
    }

    @Override
    public TextDataVal save(TextDataVal val) {
        return repository.save(val);
    }
}
