package com.alterdekim.javabot.bot;

import com.alterdekim.javabot.Constants;
import com.alterdekim.javabot.service.TextDataValService;

public class BotAccountProfileGenerator {

    public static String build(TextDataValService textDataValService, Player p) {
        return String.format(Constants.ACCOUNT,
                getStringById(textDataValService, p.getGender().getGenderTextId()),
                p.getGender().getCanDie() ? Constants.TRUE : Constants.FALSE,
                p.getGender().getIsMale() ? Constants.TRUE : Constants.FALSE,
                p.getGender().getIsFemale() ? Constants.TRUE : Constants.FALSE,
                p.getAge(),
                getStringById(textDataValService, p.getWork().getTextNameId()),
                getStringById(textDataValService, p.getWork().getTextDescId()),
                getStringById(textDataValService, p.getLuggage().getTextNameId()),
                getStringById(textDataValService, p.getLuggage().getTextDescId()),
                getStringById(textDataValService, p.getHobby().getTextDescId()),
                getStringById(textDataValService, p.getHealth().getTextNameId()),
                getStringById(textDataValService, p.getHealth().getTextDescId()),
                (int) (p.getHealth().getHealth_index()*100f),
                p.getHealth().getIsChildfree() ? Constants.TRUE : Constants.FALSE
        );
    }

    private static String getStringById(TextDataValService textDataValService, Long id) {
        return textDataValService.getTextDataValById(id).getText();
    }
}
