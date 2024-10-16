package ru.galaxy773.boxes.type;

import org.bukkit.inventory.ItemStack;
import ru.galaxy773.boxes.api.BoxReward;
import ru.galaxy773.bukkit.api.gamer.BukkitGamer;
import ru.galaxy773.multiplatform.api.gamer.constants.Group;

public class DonateBoxReward extends BoxReward {

    private final Group group;

    public DonateBoxReward(ItemStack icon, double chance, Group group) {
        super(icon, group.getChatPrefix(), chance);
        this.group = group;
    }

    @Override
    public void onReward(BukkitGamer gamer) {
        if (gamer.hasChildGroup(this.group)) {
            return;
        }
        gamer.setGroup(this.group, true);
    }

    @Override
    public void onMessage(BukkitGamer gamer) {
        gamer.sendMessageLocale("BOXES_REWARD_DONATE", this.group.getChatPrefix());
    }
}

