package ru.galaxy773.bukkit.api.gamer.leveling.type;

import lombok.AllArgsConstructor;
import ru.galaxy773.bukkit.api.gamer.BukkitGamer;
import ru.galaxy773.bukkit.api.gamer.leveling.LevelReward;
import ru.galaxy773.multiplatform.api.gamer.constants.KeysType;
import ru.galaxy773.multiplatform.api.utils.StringUtil;

@AllArgsConstructor
public class KeysLevelReward extends LevelReward {

    private final KeysType keyType;
    private final int amount;

    @Override
    public void giveReward(BukkitGamer gamer) {
        gamer.setKeys(keyType, amount, true);
    }

    @Override
    public String getLore() {
        return "\u00A78+ \u00A7d" + StringUtil.getNumberFormat(amount)
                + " \u00A77" + keyType.getName();
    }

    @Override
    public int getPriority() {
        return -4 + keyType.getId();
    }
}
