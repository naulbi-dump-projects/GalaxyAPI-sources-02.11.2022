package ru.galaxy773.multiplatform.api.utils.gamer;

import lombok.experimental.UtilityClass;

@UtilityClass
public class GamerUtil {

    private final int START_EXP = 200;

    public int getTotalExpNextLevel(int exp, int level) {
        return (int) (START_EXP * ((1 - Math.pow(1.3, level)) / -0.5));
    }

    public int getExpNextLevel(int exp, int level) {
        return getTotalExpNextLevel(exp, level) - exp;
    }

    public int getLevel(int exp) {
        int level = 1;
        while(getTotalExpNextLevel(exp, level) < exp) {
            level++;
        }
        return level;
    }
}
