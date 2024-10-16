package ru.galaxy773.menu.hooks;

import lombok.experimental.UtilityClass;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

@UtilityClass
public class EconomyHook {

    private final Economy ECONOMY = Bukkit.getServicesManager().getRegistration(Economy.class).getProvider();

    public int getBalance(Player player) {
        return (int) ECONOMY.getBalance(player);
    }

    public void depositPlayer(Player player, int money) {
        ECONOMY.depositPlayer(player, money);
    }

    public void withdrawPlayer(Player player, int money) {
        ECONOMY.withdrawPlayer(player, money);
    }
}
