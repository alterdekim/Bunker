package com.alterdekim.javabot.controller;

import com.alterdekim.javabot.dto.SynergyResult;
import com.alterdekim.javabot.entities.*;
import com.alterdekim.javabot.service.*;
import com.alterdekim.javabot.util.HashUtils;
import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
public class DatabaseController {
    private final BioService bioService;
    private final HealthService healthService;
    private final HobbyService hobbyService;
    private final LuggageService luggageService;
    private final WorkService workService;
    private final TextDataValService textDataValService;
    private final DisasterService disasterService;
    private final SynergyService synergyService;

    public DatabaseController(
            BioService bioService,
            HealthService healthService,
            HobbyService hobbyService,
            LuggageService luggageService,
            WorkService workService,
            TextDataValService textDataValService,
            DisasterService disasterService,
            SynergyService synergyService) {
        this.bioService = bioService;
        this.healthService = healthService;
        this.hobbyService = hobbyService;
        this.luggageService = luggageService;
        this.workService = workService;
        this.textDataValService = textDataValService;
        this.disasterService = disasterService;
        this.synergyService = synergyService;
    }

    private void saveGender(Map<String, String> params) {
        Boolean canDie = Boolean.parseBoolean(params.get("canDie"));
        Boolean ismale = Boolean.parseBoolean(params.get("ismale"));
        Boolean isfemale = Boolean.parseBoolean(params.get("isfemale"));
        String gender_text = new String(HashUtils.decodeHexString(params.get("gender_text")));
        TextDataVal t = textDataValService.save(new TextDataVal(gender_text));
        bioService.saveBio(new Bio(ismale, isfemale, canDie, t.getId()));
    }

    private void saveHobby(Map<String, String> params) {
        Float powerRange = Float.parseFloat(params.get("powerRange"));
        Float violenceRange = Float.parseFloat(params.get("violenceRange"));
        Float healRange = Float.parseFloat(params.get("healRange"));
        Float foodRange = Float.parseFloat(params.get("foodRange"));
        String hobby_text = new String(HashUtils.decodeHexString(params.get("hobby_text")));
        TextDataVal t = textDataValService.save(new TextDataVal(hobby_text));
        hobbyService.saveHobby(new Hobby(foodRange, powerRange, violenceRange, healRange, t.getId()));
    }

    private void saveLuggage(Map<String, String> params) {
        Float powerRange = Float.parseFloat(params.get("powerRange"));
        Float violenceRange = Float.parseFloat(params.get("violenceRange"));
        Float healRange = Float.parseFloat(params.get("healRange"));
        Float foodRange = Float.parseFloat(params.get("foodRange"));
        Boolean isGarbage = Boolean.parseBoolean(params.get("isgarbage"));

        String name_text = new String(HashUtils.decodeHexString(params.get("luggage_name_text")));
        TextDataVal t1 = textDataValService.save(new TextDataVal(name_text));

        String desc_text = new String(HashUtils.decodeHexString(params.get("luggage_desc_text")));
        TextDataVal t2 = textDataValService.save(new TextDataVal(desc_text));

        luggageService.saveLuggage(new Luggage(violenceRange, powerRange, healRange, foodRange, isGarbage, t1.getId(), t2.getId()));
    }

    private void saveHealth(Map<String, String> params) {
        Float health_index = Float.parseFloat(params.get("health_index"));
        Boolean childFree = Boolean.parseBoolean(params.get("childFree"));
        String name_text = new String(HashUtils.decodeHexString(params.get("heal_name_text")));
        TextDataVal t1 = textDataValService.save(new TextDataVal(name_text));

        String desc_text = new String(HashUtils.decodeHexString(params.get("heal_desc_text")));
        TextDataVal t2 = textDataValService.save(new TextDataVal(desc_text));

        healthService.saveHealth(new Health(health_index, t1.getId(), t2.getId(), childFree));
    }

    private void saveWork(Map<String, String> params) {
        Float powerRange = Float.parseFloat(params.get("powerRange"));
        Float violenceRange = Float.parseFloat(params.get("violenceRange"));
        Float healRange = Float.parseFloat(params.get("healRange"));
        Float foodRange = Float.parseFloat(params.get("foodRange"));

        String name_text = new String(HashUtils.decodeHexString(params.get("work_name_text")));
        TextDataVal t1 = textDataValService.save(new TextDataVal(name_text));

        String desc_text = new String(HashUtils.decodeHexString(params.get("work_desc_text")));
        TextDataVal t2 = textDataValService.save(new TextDataVal(desc_text));

        workService.saveWork(new Work(healRange, powerRange, violenceRange, foodRange, t1.getId(), t2.getId()));
    }

    private void saveDiss(Map<String, String> params) {
        String name_text = new String(HashUtils.decodeHexString(params.get("diss_name_text")));
        TextDataVal t1 = textDataValService.save(new TextDataVal(name_text));

        String desc_text = new String(HashUtils.decodeHexString(params.get("diss_desc_text")));
        TextDataVal t2 = textDataValService.save(new TextDataVal(desc_text));

        disasterService.saveDisaster(new Disaster(t1.getId(), t2.getId()));
    }

    @PostMapping("/api/remove_synergy")
    public String remove_synergy(@RequestParam Map<String, String> params) {
        Long id = Long.parseLong(params.get("synergy_id"));
        synergyService.removeById(id);
        return "ok";
    }

    @PostMapping("/api/add_synergy")
    public String add_synergy(@RequestParam Map<String, String> params) {
        Long feid = Long.parseLong(params.get("first_entity_id"));
        ColumnType fetype = ColumnType.values()[Integer.parseInt(params.get("first_entity_type"))];
        Long seid = Long.parseLong(params.get("second_entity_id"));
        ColumnType setype = ColumnType.values()[Integer.parseInt(params.get("second_entity_type"))];
        Float probability = Float.parseFloat(params.get("probability"));

        synergyService.saveSynergy(new Synergy(feid, fetype, seid, setype, probability));

        return "ok";
    }

    @PostMapping("/api/get_synergies")
    public String get_synergies(@RequestParam Map<String, String> params) {
        Long id = Long.parseLong(params.get("entity_id"));
        String section = params.get("entity_type");
        ObjectMapper mapper = new ObjectMapper();
        try {
            List<Synergy> synergyList = new ArrayList<>();
            switch (section) {
                case "agge":
                    synergyList = bioService.getSynergies(id);
                    break;
                case "lugg":
                    synergyList = luggageService.getSynergies(id);
                    break;
                case "prof":
                    synergyList = workService.getSynergies(id);
                    break;
                case "heal":
                    synergyList = healthService.getSynergies(id);
                    break;
                case "hobb":
                    synergyList = hobbyService.getSynergies(id);
                    break;
            }
            List<SynergyResult> results = new ArrayList<>();
            for( Synergy s : synergyList ) {
                String textFirst = getText(s.getFirstType(), s.getFirstEntityId());
                String textSecond = getText(s.getSecondType(), s.getSecondEntityId());
                results.add(new SynergyResult(s.getId(), textFirst, textSecond, s.getFirstType(), s.getSecondType(), s.getProbabilityValue()));
            }
            return mapper.writeValueAsString(results);
        } catch (JacksonException e) {
            log.error(e.getMessage());
        }
        return "ok";
    }

    private String getText(ColumnType type, Long feid) {
        switch (type) {
            case Bio:
                return textDataValService.getTextDataValById(bioService.getBioById(feid).getGenderTextId()).getText();
            case Health:
                return textDataValService.getTextDataValById(healthService.getHealthById(feid).getTextNameId()).getText();
            case Hobby:
                return textDataValService.getTextDataValById(hobbyService.getHobbyById(feid).getTextDescId()).getText();
            case Luggage:
                return textDataValService.getTextDataValById(luggageService.getLuggageById(feid).getTextNameId()).getText();
            case Work:
                return textDataValService.getTextDataValById(workService.getWorkById(feid).getTextNameId()).getText();
        }
        return "-";
    }

    @PostMapping("/api/add_entry")
    public String add_entry(@RequestParam Map<String, String> params) {
        /* additional data, disasters */
        String section = params.get("section");
        switch (section) {
            case "agge":
                saveGender(params);
                break;
            case "lugg":
                saveLuggage(params);
                break;
            case "prof":
                saveWork(params);
                break;
            case "heal":
                saveHealth(params);
                break;
            case "hobb":
                saveHobby(params);
                break;
            case "diss":
                saveDiss(params);
                break;
            default:
                saveDiss(params);
                break;
        }
        return "ok";
    }

    @PostMapping("/api/remove_entry")
    public String remove_entry(@RequestParam Map<String, String> params) {
        String section = params.get("section");
        long entry_id = Long.parseLong(params.get("entry_id"));
        switch (section) {
            case "agge":
                bioService.removeById(entry_id);
                break;
            case "hobb":
                hobbyService.removeById(entry_id);
                break;
            case "lugg":
                luggageService.removeById(entry_id);
                break;
            case "heal":
                healthService.removeById(entry_id);
                break;
            case "prof":
                workService.removeById(entry_id);
                break;
            case "diss":
                disasterService.removeById(entry_id);
                break;
            default:
                disasterService.removeById(entry_id);
                break;
        }
        return "ok";
    }

    @PostMapping("/api/getTextById")
    public String getText(@RequestParam Map<String, String> params) {
        long l = Long.parseLong(params.get("entry_id"));
        return textDataValService.getTextDataValById(l).getText();
    }

    @PostMapping("/api/get_entries")
    public String getEntries(@RequestParam Map<String, String> params) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            switch (params.get("section")) {
                case "agge":
                    return mapper.writeValueAsString(bioService.getAllBios());
                case "hobb":
                    return mapper.writeValueAsString(hobbyService.getAllHobbies());
                case "prof":
                    return mapper.writeValueAsString(workService.getAllWorks());
                case "heal":
                    return mapper.writeValueAsString(healthService.getAllHealth());
                case "lugg":
                    return mapper.writeValueAsString(luggageService.getAllLuggages());
                case "diss":
                    return mapper.writeValueAsString(disasterService.getAllDisasters());
                default:
                    return mapper.writeValueAsString(disasterService.getAllDisasters());
            }
        } catch (JacksonException e) {
            log.error(e.getMessage());
        }
        return "error";
    }

    @PostMapping("/api/edit_entry")
    public String edit_entry(@RequestParam Map<String, String> params) {
        ObjectMapper mapper = new ObjectMapper();
        long l = Long.parseLong(params.get("entry_id"));
        try {
            switch (params.get("section")) {
                case "agge":
                    return mapper.writeValueAsString(bioService.getBioById(l));
                case "hobb":
                    return mapper.writeValueAsString(hobbyService.getHobbyById(l));
                case "prof":
                    return mapper.writeValueAsString(workService.getWorkById(l));
                case "heal":
                    return mapper.writeValueAsString(healthService.getHealthById(l));
                case "lugg":
                    return mapper.writeValueAsString(luggageService.getLuggageById(l));
                case "diss":
                    return mapper.writeValueAsString(disasterService.getDisasterById(l));
                default:
                    return mapper.writeValueAsString(disasterService.getDisasterById(l));
            }
        } catch (JacksonException e) {
            log.error(e.getMessage());
        }
        return "error";
    }
}
