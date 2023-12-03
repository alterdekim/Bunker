package com.alterdekim.javabot;

public interface Constants {
    String REMOVE_PLAYER = "Игрок %s покидает бункер.";
    String ENDVOTE = "Голосование окончено.";
    String DRAW = "Ничья. Оба(или более) игрока уходят из игры.";
    String GROUP_SET = "Чат выбран.";
    String JOIN_GAME_BTN = "Присоединиться";
    String START_GAME_MSG = "Набор игроков начат. Присоединяйтесь.";
    String JOINED_THE_GAME = "%s добавлен(а) в игру. Всего игроков: %d";
    String ALREADY_IN_GAME = "Ты уже в игре.";
    String CANT_JOIN_NOT_STARTED = "Не могу добавить вас в игру, тк вы не разрешили вам писать сообщения.";
    String HELP = "Help";
    String START_GAME_BTN = "Начать игру";
    String NOT_ADMIN_EXCEPTION = "Вы не администратор";
    String ACCOUNT = "Ваша анкета:\nПол: %s (смертный: %s, мужчина: %s, женщина: %s, бесплоден: %s)\nВозраст: %d\nПрофессия: %s (%s)\nБагаж: %s (%s)\nХобби: %s\nЗдоровье: %s (%s) %d%%";
    String TRUE = "да";
    String FALSE = "нет";
    String PLAYERS_LESS_THAN_ZERO = "Игроков должно быть больше, чем 1.";
    String HOBBY_BTN = "Хобби";
    String WORK_BTN = "Работа";
    String HEALTH_BTN = "Здоровье";
    String AGE_BTN = "Возраст";
    String GENDER_BTN = "Гендер";
    String LUGG_BTN = "Багаж";
    String GENDER_MESAGE = "%s - пол: %s (смертный: %s, мужчина: %s, женщина: %s, бесплоден(а): %s)";
    String HEALTH_MESSAGE = "%s - здоровье: %s (%s) %d%%";
    String SHOW_TIME = "Время выбрать, какую часть анкеты показать в этом раунде?";
    String AGE_MESSAGE = "%s - возраст: %d";
    String HOBBY_MESSAGE = "%s - хобби: %s";
    String WORK_MESSAGE = "%s - профессия: %s (%s)";
    String LUGG_MESSAGE = "%s - багаж: %s (%s)";
    String DAY_MESSAGE = "Следующий раунд начался!\nВероятность выжить: %f%%";
    String POLL_QUESTION = "Кто в бункер не идёт?";

    String DEATHMATCH = "Deathmatch";

    String PROBABILITY = "Обычная";

    String WIN_MESSAGE = "Поздравляю! Победа за вами!\n%s";

    String LOSE_MESSAGE = "Поздравляю! Вы проиграли!";
}