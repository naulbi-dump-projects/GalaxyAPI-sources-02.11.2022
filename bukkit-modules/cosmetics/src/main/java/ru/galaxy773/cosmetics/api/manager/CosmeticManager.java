package ru.galaxy773.cosmetics.api.manager;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import ru.galaxy773.cosmetics.api.player.CosmeticPlayer;

public interface CosmeticManager {

    CosmeticPlayer getCosmeticPlayer(Player var1);

    void loadPlayer(Player var1);

    void unloadPlayer(Player var1);
}

