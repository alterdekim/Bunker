package com.alterdekim.javabot.service;

import com.alterdekim.javabot.entities.TextDataVal;

import java.util.List;

public interface TextDataValService {
    List<TextDataVal> getAllTexts();
    TextDataVal getTextDataValById(long textId);

    TextDataVal save(TextDataVal val);
}
