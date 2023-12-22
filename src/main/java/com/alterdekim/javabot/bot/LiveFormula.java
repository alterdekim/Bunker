package com.alterdekim.javabot.bot;

import com.alterdekim.javabot.entities.ColumnType;
import com.alterdekim.javabot.entities.Synergy;
import com.alterdekim.javabot.util.Clamp;

import java.util.List;

public class LiveFormula {
    public static double calc(List<Player> playerList, List<Synergy> synergies) {
        double i = 0;
        for( Player p : playerList ) {
            double age = 1.0D - (((double) p.getAge()) / 75.0D);
            age = p.getGender().getCanDie() ? age : 1.0D;
            double gender = p.getGender().getIsMale() ? 1 : 0;
            gender += p.getGender().getIsFemale() ? 1 : 0;
            gender = p.getHealth().getIsChildfree() ? 0 : gender;
            gender *= 0.5D;
            double work = (p.getWork().getFoodstuffs().doubleValue() +
                    p.getWork().getPower().doubleValue()) - ((p.getWork().getViolence().doubleValue() + p.getWork().getAsocial().doubleValue()) * 1.2d);
            double luggage = (p.getLuggage().getFoodstuffs().doubleValue() +
                    p.getLuggage().getPower().doubleValue()) - ((p.getLuggage().getViolence().doubleValue() + p.getLuggage().getAsocial().doubleValue()) * 1.2d);
            luggage = p.getLuggage().getGarbage() ? 0 : luggage;
            double hobby = (p.getHobby().getFoodstuffs().doubleValue() +
                    p.getHobby().getPower().doubleValue()) - ((p.getHobby().getViolence().doubleValue() + p.getHobby().getAsocial().doubleValue()) * 1.2d);
            double health = p.getHealth().getHealth_index().doubleValue();
            i += (age + gender + work + luggage + hobby + health);
        }
        i = i / ((double) playerList.size());
        double _i = i;
        for( Synergy s : synergies ) {
            Boolean fb = LiveFormula.entity(playerList, s.getFirstType(), s.getFirstEntityId());
            Boolean eb = LiveFormula.entity(playerList, s.getSecondType(), s.getSecondEntityId());
            if( fb && eb ) i += s.getProbabilityValue().doubleValue() * _i;
        }
        return Clamp.clamp(i, 0, 1);
    }

    private static Boolean entity(List<Player> players, ColumnType ct, Long eid) {
        Boolean fb = false;
        switch (ct) {
            case Bio:
                fb = LiveFormula.searchForBio(players, eid);
                break;
            case Work:
                fb = LiveFormula.searchForWork(players, eid);
                break;
            case Hobby:
                fb = LiveFormula.searchForHobby(players, eid);
                break;
            case Health:
                fb = LiveFormula.searchForHealth(players, eid);
                break;
            case Luggage:
                fb = LiveFormula.searchForLuggage(players, eid);
                break;
        }
        return fb;
    }

    private static Boolean searchForBio(List<Player> players, Long id) {
        return players.stream().anyMatch(p -> p.getGender().getId().equals(id));
    }

    private static Boolean searchForWork(List<Player> players, Long id) {
        return players.stream().anyMatch(p -> p.getWork().getId().equals(id));
    }

    private static Boolean searchForHobby(List<Player> players, Long id) {
        return players.stream().anyMatch(p -> p.getHobby().getId().equals(id));
    }

    private static Boolean searchForHealth(List<Player> players, Long id) {
        return players.stream().anyMatch(p -> p.getHealth().getId().equals(id));
    }

    private static Boolean searchForLuggage(List<Player> players, Long id) {
        return players.stream().anyMatch(p -> p.getLuggage().getId().equals(id));
    }
}
