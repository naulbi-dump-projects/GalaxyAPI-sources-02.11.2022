package ru.galaxy773.bukkit.api.gamer.leveling;

import ru.galaxy773.bukkit.api.gamer.BukkitGamer;

public abstract class LevelReward {

    public abstract void giveReward(BukkitGamer gamer);

    public abstract String getLore();

    public int getPriority() {
        return -1;
    }
}

