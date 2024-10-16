package ru.galaxy773.bukkit.api.gamer.leveling.type;

import lombok.AllArgsConstructor;
import ru.galaxy773.bukkit.api.gamer.BukkitGamer;
import ru.galaxy773.bukkit.api.gamer.leveling.LevelReward;
import ru.galaxy773.multiplatform.api.utils.StringUtil;

@AllArgsConstructor
public class GoldLevelReward extends LevelReward {

    private final int gold;

    @Override
    public void giveReward(BukkitGamer gamer) {
        gamer.setGold(gamer.getGold() + gold, true);
    }

    @Override
    public String getLore() {
        return "\u00A78+ \u00A76" + StringUtil.getNumberFormat(gold)
                + " \u00A77\u0437\u043E\u043B\u043E\u0442\u0430";
    }

    @Override
    public int getPriority() {
        return 1;
    }
}
