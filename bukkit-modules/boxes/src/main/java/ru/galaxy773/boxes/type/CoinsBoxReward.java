package ru.galaxy773.boxes.type;

import org.bukkit.inventory.ItemStack;
import ru.galaxy773.boxes.api.BoxReward;
import ru.galaxy773.bukkit.api.gamer.BukkitGamer;
import ru.galaxy773.multiplatform.api.locale.Lang;

public class CoinsBoxReward extends BoxReward {

    private final int coins;

    public CoinsBoxReward(ItemStack icon, double chance, int coins) {
        super(icon, Lang.getMessage("BOXES_REWARD_COINS_TITLE", coins), chance);
        this.coins = coins;
    }

    @Override
    public void onReward(BukkitGamer gamer) {
        gamer.setCoins(gamer.getCoins() + this.coins, true);
    }

    @Override
    public void onMessage(BukkitGamer gamer) {
        gamer.sendMessageLocale("BOXES_REWARD_COINS", this.coins);
    }
}

