package com.alterdekim.javabot.util;

import com.alterdekim.javabot.Constants;
import com.alterdekim.javabot.bot.InfoSections;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.*;

public class BotUtils {
    public static InlineKeyboardMarkup getJoinKeyboard() {
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> columns = new ArrayList<>();
        InlineKeyboardButton inlineKeyboardButton = new InlineKeyboardButton();
        inlineKeyboardButton.setText(Constants.JOIN_GAME_BTN);
        inlineKeyboardButton.setCallbackData(HashUtils.bytesToHex(Constants.JOIN_GAME_BTN.getBytes()));
        List<InlineKeyboardButton> keyboardButtonsRow1 = new ArrayList<>();
        keyboardButtonsRow1.add(inlineKeyboardButton);
        columns.add(keyboardButtonsRow1);
        InlineKeyboardButton inlineKeyboardButton1 = new InlineKeyboardButton();
        inlineKeyboardButton1.setText(Constants.START_GAME_BTN);
        inlineKeyboardButton1.setCallbackData(HashUtils.bytesToHex(Constants.START_GAME_BTN.getBytes()));
        List<InlineKeyboardButton> keyboardButtonsRow2 = new ArrayList<>();
        keyboardButtonsRow2.add(inlineKeyboardButton1);
        columns.add(keyboardButtonsRow2);
        inlineKeyboardMarkup.setKeyboard(columns);
        return inlineKeyboardMarkup;
    }

    public static InlineKeyboardMarkup getShowKeyboard(InfoSections infoSections) {
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> columns = new ArrayList<>();
        if( !infoSections.getIsHobbyShowed() ) {
            InlineKeyboardButton inlineKeyboardButton = new InlineKeyboardButton();
            inlineKeyboardButton.setText(Constants.HOBBY_BTN);
            inlineKeyboardButton.setCallbackData(HashUtils.bytesToHex(Constants.HOBBY_BTN.getBytes()));
            columns.add(Collections.singletonList(inlineKeyboardButton));
        }
        if( !infoSections.getIsAgeShowed() ) {
            InlineKeyboardButton inlineKeyboardButton = new InlineKeyboardButton();
            inlineKeyboardButton.setText(Constants.AGE_BTN);
            inlineKeyboardButton.setCallbackData(HashUtils.bytesToHex(Constants.AGE_BTN.getBytes()));
            columns.add(Collections.singletonList(inlineKeyboardButton));
        }
        if( !infoSections.getIsGenderShowed() ) {
            InlineKeyboardButton inlineKeyboardButton = new InlineKeyboardButton();
            inlineKeyboardButton.setText(Constants.GENDER_BTN);
            inlineKeyboardButton.setCallbackData(HashUtils.bytesToHex(Constants.GENDER_BTN.getBytes()));
            columns.add(Collections.singletonList(inlineKeyboardButton));
        }
        if( !infoSections.getIsHealthShowed() ) {
            InlineKeyboardButton inlineKeyboardButton = new InlineKeyboardButton();
            inlineKeyboardButton.setText(Constants.HEALTH_BTN);
            inlineKeyboardButton.setCallbackData(HashUtils.bytesToHex(Constants.HEALTH_BTN.getBytes()));
            columns.add(Collections.singletonList(inlineKeyboardButton));
        }
        if( !infoSections.getIsWorkShowed() ) {
            InlineKeyboardButton inlineKeyboardButton = new InlineKeyboardButton();
            inlineKeyboardButton.setText(Constants.WORK_BTN);
            inlineKeyboardButton.setCallbackData(HashUtils.bytesToHex(Constants.WORK_BTN.getBytes()));
            columns.add(Collections.singletonList(inlineKeyboardButton));
        }
        if( !infoSections.getIsLuggageShowed() ) {
            InlineKeyboardButton inlineKeyboardButton = new InlineKeyboardButton();
            inlineKeyboardButton.setText(Constants.LUGG_BTN);
            inlineKeyboardButton.setCallbackData(HashUtils.bytesToHex(Constants.LUGG_BTN.getBytes()));
            columns.add(Collections.singletonList(inlineKeyboardButton));
        }
        inlineKeyboardMarkup.setKeyboard(columns);
        return inlineKeyboardMarkup;
    }

    public static InlineKeyboardMarkup getGameTypeKeyboard() {
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> columns = new ArrayList<>();
        InlineKeyboardButton inlineKeyboardButton = new InlineKeyboardButton();
        inlineKeyboardButton.setText(Constants.PROBABILITY);
        inlineKeyboardButton.setCallbackData(HashUtils.bytesToHex(Constants.PROBABILITY.getBytes()));
        List<InlineKeyboardButton> keyboardButtonsRow1 = new ArrayList<>();
        keyboardButtonsRow1.add(inlineKeyboardButton);
        columns.add(keyboardButtonsRow1);
        InlineKeyboardButton inlineKeyboardButton1 = new InlineKeyboardButton();
        inlineKeyboardButton1.setText(Constants.DEATHMATCH);
        inlineKeyboardButton1.setCallbackData(HashUtils.bytesToHex(Constants.DEATHMATCH.getBytes()));
        List<InlineKeyboardButton> keyboardButtonsRow2 = new ArrayList<>();
        keyboardButtonsRow2.add(inlineKeyboardButton1);
        columns.add(keyboardButtonsRow2);
        inlineKeyboardMarkup.setKeyboard(columns);
        return inlineKeyboardMarkup;
    }

    public static String mentionUser(String name) {
        return " @"+name+" ";
    }

    public static Object getRandomFromList(List list, Random random) {
        return list.get(random.nextInt(list.size()));
    }
}
