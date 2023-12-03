package com.alterdekim.javabot.bot;

import java.util.List;

public class LiveFormula {
    public static double calc(List<Player> playerList) {
        double i = 0;
        for( Player p : playerList ) {
            double age = 1.0D - (((double) p.getAge()) / 75.0D);
            double gender = p.getGender().getIsMale() ? 1 : 0;
            gender += p.getGender().getIsFemale() ? 1 : 0;
            gender = p.getGender().getIsChildfree() ? 0 : gender;
            gender *= 0.5D;
            double work = ((p.getWork().getFood().doubleValue() +
                    p.getWork().getHeal().doubleValue() +
                    p.getWork().getPower().doubleValue()) / 3.0D) - p.getWork().getViolence().doubleValue();
            double luggage = ((p.getLuggage().getFood().doubleValue() +
                    p.getLuggage().getHeal().doubleValue() +
                    p.getLuggage().getPower().doubleValue()) / 3.0D) - p.getLuggage().getViolence().doubleValue();
            luggage = p.getLuggage().getGarbage() ? 0 : luggage;
            double hobby = ((p.getHobby().getFood().doubleValue() +
                    p.getHobby().getHeal().doubleValue() +
                    p.getHobby().getPower().doubleValue()) / 3.0D) - p.getHobby().getViolence().doubleValue();
            double health = p.getHealth().getHealth_index().doubleValue();
            i += ((age + gender + work + luggage + hobby + health) / 6.0D);
        }
        return i / ((double) playerList.size());
    }
}
