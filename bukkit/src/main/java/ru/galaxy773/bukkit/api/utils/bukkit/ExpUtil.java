package ru.galaxy773.bukkit.api.utils.bukkit;

import com.google.common.base.Preconditions;
import lombok.experimental.UtilityClass;
import org.bukkit.entity.Player;

@UtilityClass
public class ExpUtil {

    public void setTotalExperience(Player player, int exp) {
        Preconditions.checkArgument(exp > 0, "Experience is negative");
        player.setExp(0.0f);
        player.setLevel(0);
        player.setTotalExperience(0);
        int amount = exp;
        while (amount > 0) {
            int expToLevel = getExpAtLevel(player);
            amount -= expToLevel;
            if (amount >= 0) {
                player.giveExp(expToLevel);
                continue;
            }
            amount += expToLevel;
            player.giveExp(amount);
            amount = 0;
        }
    }

    private int getExpAtLevel(Player player) {
        return getExpAtLevel(player.getLevel());
    }

    public int getExpAtLevel(int level) {
        if (level <= 15) {
            return 2 * level + 7;
        }
        if (level <= 30) {
            return 5 * level - 38;
        }
        return 9 * level - 158;
    }

    public int getExpToLevel(int level) {
        int currentLevel = 0;
        int exp = 0;
        while (currentLevel < level) {
            exp += getExpAtLevel(currentLevel);
            ++currentLevel;
        }
        if (exp < 0) {
            exp = Integer.MAX_VALUE;
        }
        return exp;
    }

    public int getTotalExperience(Player player) {
        int exp = Math.round(getExpAtLevel(player) * player.getExp());
        for (int currentLevel = player.getLevel(); currentLevel > 0; --currentLevel, exp += getExpAtLevel(currentLevel)) {}
        if (exp < 0) {
            exp = Integer.MAX_VALUE;
        }
        return exp;
    }

    public int getExpUntilNextLevel(Player player) {
        int exp = Math.round(getExpAtLevel(player) * player.getExp());
        int nextLevel = player.getLevel();
        return getExpAtLevel(nextLevel) - exp;
    }
}
