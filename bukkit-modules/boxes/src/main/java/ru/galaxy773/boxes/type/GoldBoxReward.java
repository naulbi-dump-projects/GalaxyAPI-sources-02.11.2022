package ru.galaxy773.boxes.type;

import org.bukkit.inventory.ItemStack;
import ru.galaxy773.boxes.api.BoxReward;
import ru.galaxy773.bukkit.api.gamer.BukkitGamer;
import ru.galaxy773.multiplatform.api.locale.Lang;

public class GoldBoxReward extends BoxReward {

    private final int gold;

    public GoldBoxReward(ItemStack icon, double chance, int gold) {
        super(icon, Lang.getMessage("BOXES_REWARD_GOLD_TITLE", gold), chance);
        this.gold = gold;
    }

    @Override
    public void onReward(BukkitGamer gamer) {
        gamer.setGold(gamer.getGold() + this.gold, true);
    }

    @Override
    public void onMessage(BukkitGamer gamer) {
        gamer.sendMessageLocale("BOXES_REWARD_GOLD", this.gold);
    }
}

