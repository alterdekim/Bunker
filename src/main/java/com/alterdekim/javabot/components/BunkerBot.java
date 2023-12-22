package com.alterdekim.javabot.components;

import com.alterdekim.javabot.bot.*;
import com.alterdekim.javabot.Commands;
import com.alterdekim.javabot.Constants;
import com.alterdekim.javabot.TelegramConfig;
import com.alterdekim.javabot.entities.*;
import com.alterdekim.javabot.service.*;
import com.alterdekim.javabot.util.BotUtils;
import com.alterdekim.javabot.util.GameState;
import com.alterdekim.javabot.util.HashUtils;
import jakarta.validation.constraints.NotNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.AnswerCallbackQuery;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.groupadministration.GetChatMember;
import org.telegram.telegrambots.meta.api.methods.polls.SendPoll;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.DeleteMessage;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.api.objects.chatmember.ChatMember;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.Serializable;
import java.util.*;
import java.util.concurrent.*;
import java.util.stream.Collectors;

@Component
@Slf4j
public class BunkerBot extends TelegramLongPollingBot {
    private final TelegramConfig telegramConfig;
    private String groupId = "";

    private GameState gameState;

    private List<Player> players;

    private final BioService bioService;
    private final HealthService healthService;
    private final HobbyService hobbyService;
    private final LuggageService luggageService;
    private final WorkService workService;
    private final TextDataValService textDataValService;
    private final DisasterService disasterService;
    private final SynergyService synergyService;

    private final Random random;

    private DayNightFields dayNightFields;

    private ConcurrentLinkedQueue<BotApiMethod<? extends Serializable>> linkedQueue;

    @SuppressWarnings("deprecation")
    public BunkerBot(TelegramConfig telegramConfig,
                     BioService bioService,
                     HealthService healthService,
                     HobbyService hobbyService,
                     LuggageService luggageService,
                     WorkService workService,
                     TextDataValService textDataValService,
                     DisasterService disasterService,
                     SynergyService synergyService) {
        this.telegramConfig = telegramConfig;
        this.players = new ArrayList<>();
        this.gameState = GameState.NONE;
        this.bioService = bioService;
        this.healthService = healthService;
        this.hobbyService = hobbyService;
        this.luggageService = luggageService;
        this.workService = workService;
        this.textDataValService = textDataValService;
        this.disasterService = disasterService;
        this.synergyService = synergyService;
        this.random = new Random();
        this.dayNightFields = new DayNightFields();
        this.linkedQueue = new ConcurrentLinkedQueue<>();
    }

    @Scheduled(fixedRate = 100)
    private void executeApi() {
        if( !this.linkedQueue.isEmpty() )
            sendApiMethodAsync(this.linkedQueue.poll());
    }


    @Override
    public String getBotUsername() {
        return telegramConfig.getBotLogin();
    }

    @Override
    @SuppressWarnings("deprecation")
    public String getBotToken() {
        return telegramConfig.getBotToken();
    }

    private Long getMasterId() {
        return Long.parseLong(telegramConfig.getMasterId());
    }

    private void joinGame(User user, Integer msgid) {
        if( gameState != GameState.JOINING )
            return;

        if( !hasPlayerWithId(user.getId()) ) {
            if( canWrite(user) ) {
                this.players.add(new Player(user.getId(), user.getFirstName()));
                sendApi(
                        new SendMessage(
                                groupId,
                                String.format(
                                        Constants.JOINED_THE_GAME,
                                        user.getFirstName(),
                                        players.size()
                                )
                        )
                );
                return;
            }

            if( msgid != 0 ) {
                SendMessage sm = new SendMessage(groupId, Constants.CANT_JOIN_NOT_STARTED);
                sm.setReplyToMessageId(msgid);
                sendApi(sm);
                return;
            }
            SendMessage sm = new SendMessage(groupId, BotUtils.mentionUser(user.getUserName()) + Constants.CANT_JOIN_NOT_STARTED);
            sendApi(sm);
            return;
        }
        sendApi(
                new SendMessage(
                        groupId,
                        Constants.ALREADY_IN_GAME
                )
        );
    }

    private Boolean canWrite(User u) {
        SendMessage message = new SendMessage(u.getId()+"", ".");
        message.disableNotification();
        Message mr;
        try {
            mr = sendApiMethod(message);
        } catch ( Exception e ) {
            return false;
        }
        sendApi(new DeleteMessage(u.getId()+"", mr.getMessageId()));
        return true;
    }

    private Boolean isAdmin(User u) {
        GetChatMember chatMember = new GetChatMember(groupId, u.getId());
        try {
            ChatMember cm = sendApiMethod(chatMember);
            return cm.getStatus().equals("creator") || cm.getStatus().startsWith("admin");
        } catch (Exception e) {
            return false;
        }
    }

    private void startGame() {
        if( gameState != GameState.JOINING )
            return;
        if(players.size() < 2) {
            sendApi(new SendMessage(groupId, Constants.PLAYERS_LESS_THAN_ZERO));
            return;
        }
        this.gameState = GameState.STARTED;
        Disaster d = (Disaster) BotUtils.getRandomFromList(disasterService.getAllDisasters(), random);
        sendApi(new SendMessage(groupId, getStringById(d.getDescTextId())));
        List<Bio> bios = bioService.getAllBios();
        List<Work> works = workService.getAllWorks();
        List<Luggage> luggs = luggageService.getAllLuggages();
        List<Hobby> hobbies = hobbyService.getAllHobbies();
        List<Health> healths = healthService.getAllHealth();
        for( Player p : players ) {
            p.setAge(random.nextInt(57)+18);
            p.setGender((Bio) BotUtils.getRandomFromList(bios, random));
            p.setWork((Work) BotUtils.getRandomFromList(works, random));
            p.setLuggage((Luggage) BotUtils.getRandomFromList(luggs, random));
            p.setHobby((Hobby) BotUtils.getRandomFromList(hobbies, random));
            p.setHealth((Health) BotUtils.getRandomFromList(healths, random));

            SendMessage sendMessage = new SendMessage(p.getTelegramId()+"", BotAccountProfileGenerator.build(textDataValService, p));
            sendApi(sendMessage);
        }
        doNight();
    }

    private void doNight() {
        this.dayNightFields.setIsNight(true);
        this.dayNightFields.setNightToken(random.nextInt(1000)+10);
        this.dayNightFields.setPoll(new HashMap<>());
        this.dayNightFields.setTurnCount(this.dayNightFields.getTurnCount()+1);
        for( Player p : players ) {
            SendMessage sendMessage = new SendMessage(p.getTelegramId()+"", Constants.SHOW_TIME);
            sendMessage.setReplyMarkup(BotUtils.getShowKeyboard(p.getInfoSections()));
            sendApi(sendMessage);
        }
    }

    private String getStringById(Long id) {
        return textDataValService.getTextDataValById(id).getText();
    }

    private void processNightButton(CallbackQuery callbackQuery) {
        if( this.hasPlayerWithId(callbackQuery.getFrom().getId()) &&
                !getPlayerById(callbackQuery.getFrom().getId()).getIsAnswered() ) {
            Player p = getPlayerById(callbackQuery.getFrom().getId());
            InfoSections ins = p.getInfoSections();
            switch (new String(HashUtils.decodeHexString(callbackQuery.getData()))) {
                case Constants.GENDER_BTN:
                    dayNightFields.appendMessage(String.format(Constants.GENDER_MESAGE, callbackQuery.getFrom().getFirstName(),  getStringById(p.getGender().getGenderTextId()),
                            p.getGender().getCanDie() ? Constants.TRUE : Constants.FALSE,
                            p.getGender().getIsMale() ? Constants.TRUE : Constants.FALSE,
                            p.getGender().getIsFemale() ? Constants.TRUE : Constants.FALSE) + "\n");
                    ins.setIsGenderShowed(true);
                    break;
                case Constants.HEALTH_BTN:
                    dayNightFields.appendMessage(String.format(Constants.HEALTH_MESSAGE, callbackQuery.getFrom().getFirstName(), getStringById(p.getHealth().getTextNameId()),
                            getStringById(p.getHealth().getTextDescId()),
                            (int) (p.getHealth().getHealth_index()*100f),
                            p.getHealth().getIsChildfree() ? Constants.TRUE : Constants.FALSE) + "\n");
                    ins.setIsHealthShowed(true);
                    break;
                case Constants.AGE_BTN:
                    dayNightFields.appendMessage(String.format(Constants.AGE_MESSAGE, callbackQuery.getFrom().getFirstName(), p.getAge()) + "\n");
                    ins.setIsAgeShowed(true);
                    break;
                case Constants.HOBBY_BTN:
                    dayNightFields.appendMessage(String.format(Constants.HOBBY_MESSAGE, callbackQuery.getFrom().getFirstName(),
                            getStringById(p.getHobby().getTextDescId())) + "\n");
                    ins.setIsHobbyShowed(true);
                    break;
                case Constants.LUGG_BTN:
                    dayNightFields.appendMessage(String.format(Constants.LUGG_MESSAGE, callbackQuery.getFrom().getFirstName(),
                            getStringById(p.getLuggage().getTextNameId()),
                            getStringById(p.getLuggage().getTextDescId())) + "\n");
                    ins.setIsLuggageShowed(true);
                    break;
                case Constants.WORK_BTN:
                    dayNightFields.appendMessage(String.format(Constants.WORK_MESSAGE, callbackQuery.getFrom().getFirstName(),
                            getStringById(p.getWork().getTextNameId()),
                            getStringById(p.getWork().getTextDescId())) + "\n");
                    ins.setIsWorkShowed(true);
                    break;
            }
            setIsAnswered(callbackQuery.getFrom().getId());
            updateInfoSections(p, ins);
            sendApi(new SendMessage(callbackQuery.getMessage().getChatId()+"", Constants.THANK_YOU));
            sendApi(new DeleteMessage(callbackQuery.getMessage().getChatId()+"", callbackQuery.getMessage().getMessageId()));
            if( isAllAnswered() )
                doDay();
        }
    }
    
    private void sendApi(BotApiMethod<? extends Serializable> method) {
        this.linkedQueue.add(method);
    }

    private void updateInfoSections(Player p1, InfoSections infoSections) {
        players.stream().filter((p) -> p.equals(p1)).forEach((p) -> p.setInfoSections(infoSections));
    }

    private void setIsAnswered(Long id) {
        players.stream().filter(p -> p.getTelegramId().equals(id) ).forEach(p -> p.setIsAnswered(true));
    }

    private void setIsVoted(Long id) {
        players.stream().filter(p -> p.getTelegramId().equals(id) ).forEach(p -> p.setIsVoted(true));
    }

    private void doDay() {
        dayNightFields.setIsNight(false);
        sendApi(new SendMessage(groupId, String.format(Constants.DAY_MESSAGE, Math.floor(LiveFormula.calc(players, synergyService.getAllSynergies())*100d))));
        sendApi(new SendMessage(groupId, dayNightFields.getDayMessage()));
        dayNightFields.setDayMessage("");
        setAllNotAnswered();
        setAllNotVoted();
        SendPoll sp = new SendPoll(groupId, Constants.POLL_QUESTION, getAllUsers());
        sp.setIsAnonymous(false);
        sp.setAllowMultipleAnswers(false);
        try {
            dayNightFields.setLastPollId(sendApiMethod(sp).getPoll().getId());
        } catch (TelegramApiException e) {
            log.error(e.getMessage());
        }
    }

    private void setAllNotVoted() {
        players.forEach((p) -> p.setIsVoted(false));
    }

    private Boolean isAllVoted() {
        return players.stream().allMatch(Player::getIsVoted);
    }

    private List<String> getAllUsers() {
        return players.stream().map(Player::getFirstName).collect(Collectors.toList());
    }

    private void setAllNotAnswered() {
        players.forEach((p) -> p.setIsAnswered(false));
    }

    private Boolean isAllAnswered() {
        return players.stream().allMatch(Player::getIsAnswered);
    }

    private void showInfo() {
        if( this.dayNightFields.getIsNight() ) {
            sendApi(new SendMessage(groupId, Constants.CANT_SEND_NOT_DAY));
            return;
        }
        String message = Constants.INFO_MESSAGE;
        for( Player p : players ) {
            message += p.getFirstName() + ":\n";
            InfoSections s = p.getInfoSections();
            if(s.getIsGenderShowed()) {
                message += String.format(Constants.GENDER_MESAGE, p.getFirstName(),  getStringById(p.getGender().getGenderTextId()),
                        p.getGender().getCanDie() ? Constants.TRUE : Constants.FALSE,
                        p.getGender().getIsMale() ? Constants.TRUE : Constants.FALSE,
                        p.getGender().getIsFemale() ? Constants.TRUE : Constants.FALSE) + "\n";
            }
            if(s.getIsAgeShowed()) {
                message += String.format(Constants.AGE_MESSAGE, p.getFirstName(), p.getAge()) + "\n";
            }
            if(s.getIsLuggageShowed()) {
                message += String.format(Constants.LUGG_MESSAGE, p.getFirstName(),
                        getStringById(p.getLuggage().getTextNameId()),
                        getStringById(p.getLuggage().getTextDescId())) + "\n";
            }
            if(s.getIsHealthShowed()) {
                message += String.format(Constants.HEALTH_MESSAGE, p.getFirstName(), getStringById(p.getHealth().getTextNameId()),
                        getStringById(p.getHealth().getTextDescId()),
                        (int) (p.getHealth().getHealth_index()*100f),
                        p.getHealth().getIsChildfree() ? Constants.TRUE : Constants.FALSE) + "\n";
            }
            if(s.getIsWorkShowed()) {
                message += String.format(Constants.WORK_MESSAGE, p.getFirstName(),
                        getStringById(p.getWork().getTextNameId()),
                        getStringById(p.getWork().getTextDescId())) + "\n";
            }
            if(s.getIsHobbyShowed()) {
                message += String.format(Constants.HOBBY_MESSAGE, p.getFirstName(),
                        getStringById(p.getHobby().getTextDescId())) + "\n";
            }
            message += "\n";
        }
        sendApi(new SendMessage(groupId, message));
    }

    private void endVote() {
        Integer max = dayNightFields.getPoll().values().stream().max(Integer::compareTo).get();
        long count = dayNightFields.getPoll().values().stream().filter(p -> p.equals(max)).count();
        SendMessage sendMessage = new SendMessage(groupId, Constants.ENDVOTE);
        if( count > 1 ) {
            sendMessage = new SendMessage(groupId, Constants.DRAW);
        } else {
            removeVotePlayers(max);
        }
        sendApi(sendMessage);
        if( !checkEndGame() ) {
            doNight();
            return;
        }
        endGame();
    }

    private Boolean checkEndGame() {
        return players.size() < 2 || dayNightFields.getTurnCount() >= 6;
    }

    private void endGame() {
        double d = Math.floor(LiveFormula.calc(players, synergyService.getAllSynergies())*100d);
        sendApi(new SendMessage(groupId, String.format(Constants.END_GAME, d)));
        if(!players.isEmpty() && Math.floor(random.nextDouble()*100d) <= d) {
            sendApi(new SendMessage(groupId, String.format(Constants.WIN_MESSAGE,
                    players.stream().map(Player::getFirstName).collect(Collectors.joining(", "))
                    )));
        } else {
            sendApi(new SendMessage(groupId, Constants.LOSE_MESSAGE));
        }
        interruptGame();
    }

    private void removeVotePlayers(Integer max) {
        dayNightFields.getPoll()
                .entrySet()
                .stream()
                .filter(e -> e.getValue().equals(max))
                .forEach(i -> {
                    sendApi(new SendMessage(groupId, String.format(Constants.REMOVE_PLAYER, players.get(i.getKey()).getFirstName())));
                    players.remove(i.getKey().intValue());
                });
    }

    private void interruptGame() {
        this.dayNightFields = new DayNightFields();
        this.players = new ArrayList<>();
        this.gameState = GameState.NONE;
    }

    @Override
    public void onUpdateReceived(@NotNull Update update) {
        if( update.hasPollAnswer() && update.getPollAnswer().getPollId().equals(dayNightFields.getLastPollId())
                && !dayNightFields.getIsNight() && !update.getPollAnswer().getOptionIds().isEmpty()) {
            if( getPlayerById(update.getPollAnswer().getUser().getId()).getIsVoted() )
                return;
            if( dayNightFields.getPoll().containsKey(update.getPollAnswer().getOptionIds().get(0)) ) {
                dayNightFields.getPoll().put(update.getPollAnswer().getOptionIds().get(0),
                        dayNightFields.getPoll().get(update.getPollAnswer().getOptionIds().get(0)) + 1);
            } else {
                dayNightFields.getPoll().put(update.getPollAnswer().getOptionIds().get(0), 1);
            }
            setIsVoted(update.getPollAnswer().getUser().getId());
            sendApi(new SendMessage(groupId, String.format(Constants.USER_VOTED, update.getPollAnswer().getUser().getFirstName())));
            if( isAllVoted() )
                endVote();
            return;
        }

        if( update.hasCallbackQuery() ) {
            sendApi(
                    new AnswerCallbackQuery(
                            update.getCallbackQuery().getId()
                    )
            );
            if( update.getCallbackQuery().getData().equals(HashUtils.bytesToHex(Constants.JOIN_GAME_BTN.getBytes())) ) {
                joinGame(update.getCallbackQuery().getFrom(), 0);
            } else if( update.getCallbackQuery().getData().equals(HashUtils.bytesToHex(Constants.START_GAME_BTN.getBytes())) ) {
                if( isAdmin(update.getCallbackQuery().getFrom()) ) {
                    startGame();
                    return;
                }
                sendApi(new SendMessage(groupId, Constants.NOT_ADMIN_EXCEPTION));
            } else if( gameState == GameState.STARTED && dayNightFields.getIsNight() ) {
                processNightButton(update.getCallbackQuery());
            }
            return;
        }

        if( !(update.hasMessage() && update.getMessage().hasText() && update.getMessage().isCommand()) )
            return;

        String chatId = update.getMessage().getChatId()+"";

        if( ( update.getMessage().getText().equals(Commands.SET_GROUP + "@" + getBotUsername()) ||
                update.getMessage().getText().equals(Commands.SET_GROUP)) &&
                update.getMessage().getFrom().getId().equals(getMasterId()) && gameState == GameState.NONE) {
            groupId = chatId;
            sendApi(new SendMessage(chatId, Constants.GROUP_SET));
        }

        if( !chatId.equals(groupId) )
            return;

        if ( (update.getMessage().getText().equals(Commands.START_GAME + "@" + getBotUsername()) ||
                update.getMessage().getText().equals(Commands.START_GAME) ) &&
                gameState == GameState.NONE) {
            SendMessage message = new SendMessage(chatId, Constants.START_GAME_MSG);
            message.setReplyMarkup(BotUtils.getJoinKeyboard());
            sendApi(message);
            gameState = GameState.JOINING;
        } else if (update.getMessage().getText().equals(Commands.JOIN_GAME + "@" + getBotUsername()) ||
                update.getMessage().getText().equals(Commands.JOIN_GAME)) {
            joinGame(update.getMessage().getFrom(), update.getMessage().getMessageId());
        } else if (update.getMessage().getText().equals(Commands.HELP + "@" + getBotUsername()) ||
                update.getMessage().getText().equals(Commands.HELP)) {
            sendApi(new SendMessage(chatId, Constants.HELP));
        } else if ((update.getMessage().getText().equals(Commands.STOP_GAME + "@" + getBotUsername()) ||
                update.getMessage().getText().equals(Commands.STOP_GAME) ) &&
                gameState != GameState.NONE) {
            if( isAdmin(update.getMessage().getFrom()) ) {
                sendApi(new SendMessage(groupId, Constants.INTERRUPT_GAME));
                interruptGame();
                return;
            }
            sendApi(new SendMessage(groupId, Constants.NOT_ADMIN_EXCEPTION));
        } else if((update.getMessage().getText().equals(Commands.GET_INFO + "@" + getBotUsername()) ||
                update.getMessage().getText().equals(Commands.GET_INFO) ) &&
                gameState == GameState.STARTED) {
            showInfo();
        }
    }

    private Player getPlayerById(Long id) {
        return players.stream().filter((p) -> p.getTelegramId().equals(id) ).findFirst().orElse(null);
    }

    private Boolean hasPlayerWithId(Long id) {
        return players.stream().anyMatch((p) -> p.getTelegramId().equals(id));
    }
}