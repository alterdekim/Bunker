package com.alterdekim.javabot;

public interface Constants {
    String REMOVE_PLAYER = "Игрок %s покидает бункер.";
    String ENDVOTE = "Голосование окончено.";
    String DRAW = "Ничья. Никто не уходит из игры.";
    String GROUP_SET = "Чат выбран.";
    String INTERRUPT_GAME = "Игра остановлена.";
    String USER_VOTED = "%s проголосовал.";
    String JOIN_GAME_BTN = "Присоединиться";
    String START_GAME_MSG = "Набор игроков начат. Присоединяйтесь.";
    String JOINED_THE_GAME = "%s добавлен(а) в игру. Всего игроков: %d";
    String THANK_YOU = "Спасибо.";
    String ALREADY_IN_GAME = "Ты уже в игре.";
    String CANT_JOIN_NOT_STARTED = "Не могу добавить вас в игру, тк вы не разрешили вам писать сообщения.";
    String HELP = "Help";
    String START_GAME_BTN = "Начать игру";
    String NOT_ADMIN_EXCEPTION = "Вы не администратор";
    String ACCOUNT = "Ваша анкета:\nПол: %s (смертный: %s, мужчина: %s, женщина: %s)\nВозраст: %d\nПрофессия: %s (%s)\nБагаж: %s (%s)\nХобби: %s\nЗдоровье: %s (%s) %d%%, бесплоден: %s";
    String TRUE = "да";
    String FALSE = "нет";
    String PLAYERS_LESS_THAN_ZERO = "Игроков должно быть больше, чем 1.";
    String HOBBY_BTN = "Хобби";
    String WORK_BTN = "Работа";
    String HEALTH_BTN = "Здоровье";
    String AGE_BTN = "Возраст";
    String GENDER_BTN = "Гендер";
    String LUGG_BTN = "Багаж";
    String GENDER_MESAGE = "%s - пол: %s (смертный: %s, мужчина: %s, женщина: %s)";
    String HEALTH_MESSAGE = "%s - здоровье: %s (%s) %d%%, бесплоден(а): %s";
    String SHOW_TIME = "Время выбрать, какую часть анкеты показать в этом раунде?";
    String AGE_MESSAGE = "%s - возраст: %d";
    String HOBBY_MESSAGE = "%s - хобби: %s";
    String WORK_MESSAGE = "%s - профессия: %s (%s)";
    String LUGG_MESSAGE = "%s - багаж: %s (%s)";
    String DAY_MESSAGE = "Следующий раунд начался!\nВероятность выжить: %f%%";
    String END_GAME = "Конец игры.\nВероятность выжить: %f%%";
    String POLL_QUESTION = "Кто в бункер не идёт?";

    String DEATHMATCH = "Deathmatch";

    String PROBABILITY = "Обычная";

    String WIN_MESSAGE = "Поздравляю! Победа за вами!\n%s";

    String LOSE_MESSAGE = "Поздравляю! Вы проиграли!";

    String INFO_MESSAGE = "Вот открытая информация о живых игроках\n";

    String CANT_SEND_NOT_DAY = "Нельзя использовать эту команду во время перерыва.";
}