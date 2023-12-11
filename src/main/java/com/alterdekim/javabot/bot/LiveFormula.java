package com.alterdekim.javabot.bot;

import com.alterdekim.javabot.util.Clamp;

import java.util.List;

public class LiveFormula {
    public static double calc(List<Player> playerList) {
        double i = 0;
        boolean hasWorkingFemale = false;
        boolean hasWorkingMale = false;
        for( Player p : playerList ) {
            double age = 1.0D - (((double) p.getAge()) / 75.0D);
            age = p.getGender().getCanDie() ? age : 1.0D;
            double gender = p.getGender().getIsMale() ? 1 : 0;
            gender += p.getGender().getIsFemale() ? 1 : 0;
            gender = p.getGender().getIsChildfree() ? 0 : gender;
            gender *= 0.5D;
            if( p.getGender().getIsMale() && !p.getGender().getIsChildfree() ) {
                hasWorkingMale = true;
            }
            if( p.getGender().getIsFemale() && !p.getGender().getIsChildfree() ) {
                hasWorkingFemale = true;
            }
            double work = ((p.getWork().getFood().doubleValue() +
                    p.getWork().getHeal().doubleValue() +
                    p.getWork().getPower().doubleValue()) / 2.0D) - (p.getWork().getViolence().doubleValue() * 1.2d);
            double luggage = ((p.getLuggage().getFood().doubleValue() +
                    p.getLuggage().getHeal().doubleValue() +
                    p.getLuggage().getPower().doubleValue()) / 2.0D) - (p.getLuggage().getViolence().doubleValue() * 1.2d);
            luggage = p.getLuggage().getGarbage() ? 0 : luggage;
            double hobby = ((p.getHobby().getFood().doubleValue() +
                    p.getHobby().getHeal().doubleValue() +
                    p.getHobby().getPower().doubleValue()) / 2.0D) - (p.getHobby().getViolence().doubleValue() * 1.2d);
            double health = p.getHealth().getHealth_index().doubleValue();
            i += ((age + gender + work + luggage + hobby + health) / 6.0D);
        }
        i = i / ((double) playerList.size());
        i = hasWorkingMale && hasWorkingFemale ? i + 0.15d : i - 0.15d;
        return Clamp.clamp(i, 0, 1);
    }
}
