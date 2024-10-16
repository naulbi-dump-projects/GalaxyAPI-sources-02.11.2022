package ru.galaxy773.bukkit.api.gamer.leveling.type;

import lombok.AllArgsConstructor;
import ru.galaxy773.bukkit.api.gamer.BukkitGamer;
import ru.galaxy773.bukkit.api.gamer.leveling.LevelReward;
import ru.galaxy773.multiplatform.api.locale.Lang;

@AllArgsConstructor
public class MessageLevelReward extends LevelReward {

    private final String key;

    @Override
    public void giveReward(BukkitGamer gamer) {
        //nothing
    }

    @Override
    public String getLore() {
        return Lang.getMessage(key);
    }
}
