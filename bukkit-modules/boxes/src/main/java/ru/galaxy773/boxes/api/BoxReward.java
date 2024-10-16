package ru.galaxy773.boxes.api;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.bukkit.inventory.ItemStack;
import ru.galaxy773.bukkit.api.gamer.BukkitGamer;

@AllArgsConstructor
@Getter
public abstract class BoxReward {

    protected final ItemStack icon;
    protected final String title;
    protected final double chance;

    public abstract void onReward(BukkitGamer gamer);

    public abstract void onMessage(BukkitGamer gamer);
}

