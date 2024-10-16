package ru.galaxy773.boxes.type;

import org.bukkit.inventory.ItemStack;
import ru.galaxy773.boxes.api.BoxReward;
import ru.galaxy773.bukkit.api.gamer.BukkitGamer;
import ru.galaxy773.multiplatform.api.gamer.customization.tituls.TitulType;
import ru.galaxy773.multiplatform.api.locale.Lang;

public class TitulsBoxReward extends BoxReward {

    private final TitulType titulType;

    public TitulsBoxReward(ItemStack icon, double chance, TitulType titulType) {
        super(icon, Lang.getMessage("BOXES_REWARD_TITULS_TITLE", titulType.getTitul().getTitul()), chance);
        this.titulType = titulType;
    }

    @Override
    public void onReward(BukkitGamer gamer) {
        gamer.addTitul(this.titulType);
    }

    @Override
    public void onMessage(BukkitGamer gamer) {
        gamer.sendMessageLocale("BOXES_REWARD_TITUL", this.titulType.getTitul().getTitul());
    }
}

