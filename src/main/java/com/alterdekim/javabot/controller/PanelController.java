package com.alterdekim.javabot.controller;

import com.alterdekim.javabot.entities.*;
import com.alterdekim.javabot.service.*;
import com.alterdekim.javabot.util.UAgentInfo;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.Serializable;
import java.util.*;

@Slf4j
@Controller
public class PanelController {
    private final BioService bioService;
    private final HealthService healthService;
    private final HobbyService hobbyService;
    private final LuggageService luggageService;
    private final WorkService workService;
    private final TextDataValService textDataValService;
    private final DisasterService disasterService;

    public PanelController(
            BioService bioService,
            HealthService healthService,
            HobbyService hobbyService,
            LuggageService luggageService,
            WorkService workService,
            TextDataValService textDataValService,
            DisasterService disasterService) {
        this.bioService = bioService;
        this.healthService = healthService;
        this.hobbyService = hobbyService;
        this.luggageService = luggageService;
        this.workService = workService;
        this.textDataValService = textDataValService;
        this.disasterService = disasterService;
    }

    private List<Card> dissToCards() {
        List<Disaster> bios = disasterService.getAllDisasters();
        List<Card> cards = new ArrayList<>();
        for( Disaster b : bios ) {
            Card card = new Card();
            card.setId(b.getId());
            card.setTitle(textDataValService.getTextDataValById(b.getNameTextId()).getText());
            card.setBody(Collections.singletonList("Description: " + textDataValService.getTextDataValById(b.getDescTextId()).getText()));
            cards.add(card);
        }
        cards.sort(Comparator.comparing(Card::getId));
        Collections.reverse(cards);
        return cards;
    }

    private List<Card> bioToCards() {
        List<Bio> bios = bioService.getAllBios();
        List<Card> cards = new ArrayList<>();
        for( Bio b : bios ) {
            Card card = new Card();
            card.setId(b.getId());
            card.setTitle(textDataValService.getTextDataValById(b.getGenderTextId()).getText());
            card.setBody(Arrays.asList("canDie: " + b.getCanDie(), "isMale: " + b.getIsMale(), "isFemale: " + b.getIsFemale()));
            cards.add(card);
        }
        cards.sort(Comparator.comparing(Card::getId));
        Collections.reverse(cards);
        return cards;
    }

    private List<Card> hobbyToCards() {
        List<Hobby> bios = hobbyService.getAllHobbies();
        List<Card> cards = new ArrayList<>();
        for( Hobby b : bios ) {
            Card card = new Card();
            card.setId(b.getId());
            card.setTitle(textDataValService.getTextDataValById(b.getTextDescId()).getText());
            card.setBody(Arrays.asList("Foodstuffs: " + b.getFoodstuffs(), "Asocial: " + b.getAsocial(), "Power: " + b.getPower(), "Violence:" + b.getViolence()));
            cards.add(card);
        }
        cards.sort(Comparator.comparing(Card::getId));
        Collections.reverse(cards);
        return cards;
    }

    private List<Card> workToCards() {
        List<Work> bios = workService.getAllWorks();
        List<Card> cards = new ArrayList<>();
        for( Work b : bios ) {
            Card card = new Card();
            card.setId(b.getId());
            card.setTitle(textDataValService.getTextDataValById(b.getTextNameId()).getText());
            card.setBody(Arrays.asList("Foodstuffs: " + b.getFoodstuffs(), "Power: " + b.getPower(), "Violence: " + b.getViolence(), "Asocial: " + b.getAsocial(), "Description: " + textDataValService.getTextDataValById(b.getTextDescId()).getText()));
            cards.add(card);
        }
        cards.sort(Comparator.comparing(Card::getId));
        Collections.reverse(cards);
        return cards;
    }

    private List<Card> healToCards() {
        List<Health> bios = healthService.getAllHealth();
        List<Card> cards = new ArrayList<>();
        for( Health b : bios ) {
            Card card = new Card();
            card.setId(b.getId());
            card.setTitle(textDataValService.getTextDataValById(b.getTextNameId()).getText());
            card.setBody(Arrays.asList("Health index: " + b.getHealth_index(),"isChildfree: " + b.getIsChildfree(), "Description: " + textDataValService.getTextDataValById(b.getTextDescId()).getText()));
            cards.add(card);
        }
        cards.sort(Comparator.comparing(Card::getId));
        Collections.reverse(cards);
        return cards;
    }

    private List<Card> luggageToCards() {
        List<Luggage> bios = luggageService.getAllLuggages();
        List<Card> cards = new ArrayList<>();
        for( Luggage b : bios ) {
            Card card = new Card();
            card.setId(b.getId());
            card.setTitle(textDataValService.getTextDataValById(b.getTextNameId()).getText());
            card.setBody(Arrays.asList("Foodstuffs: " + b.getFoodstuffs(), "Power: " + b.getPower(), "Asocial: " + b.getAsocial(), "Violence: " + b.getViolence(), "Is garbage: " + b.getGarbage(), "Description: " + textDataValService.getTextDataValById(b.getTextDescId()).getText()));
            cards.add(card);
        }
        cards.sort(Comparator.comparing(Card::getId));
        Collections.reverse(cards);
        return cards;
    }

    @GetMapping("/panel")
    public String panelPage(Model model, @RequestHeader("User-Agent") String uagent, @RequestHeader("Accept") String accepth, @RequestParam(value = "section", defaultValue = "diss") String section) {
        model.addAttribute("is_mobile", new UAgentInfo(uagent, accepth).detectSmartphone());
        model.addAttribute("section", section);
        switch (section) {
            case "lugg":
                model.addAttribute( "cards", luggageToCards() );
                break;
            case "heal":
                model.addAttribute("cards", healToCards() );
                break;
            case "prof":
                model.addAttribute("cards", workToCards() );
                break;
            case "hobb":
                model.addAttribute("cards", hobbyToCards() );
                break;
            case "agge":
                model.addAttribute("cards", bioToCards() );
                break;
            case "diss":
                model.addAttribute("cards", dissToCards() );
                break;
        }
        return "panel";
    }

    @Getter
    @Setter
    @NoArgsConstructor
    private static class Card implements Serializable {
        private String title;
        private List<String> body;
        private Long id;
    }
}
