package ru.galaxy773.bukkit.api.utils.economy;

import lombok.experimental.UtilityClass;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

@UtilityClass
public class EconomyUtil {

    private static final Economy ECONOMY = Bukkit.getServicesManager()
            .getRegistration(Economy.class).getProvider();

    public int getBalance(Player player) {
        return getBalance(player.getName());
    }

    public int getBalance(String playerName) {
        return (int) ECONOMY.getBalance(playerName);
    }

    public void deposit(Player player, int money) {
        deposit(player.getName(), money);
    }

    public void deposit(String playerName, int money) {
        ECONOMY.depositPlayer(playerName, money);
    }

    public void withdraw(Player player, int money) {
        withdraw(player.getName(), money);
    }

    public void withdraw(String playerName, int money) {
        ECONOMY.withdrawPlayer(playerName, money);
    }
}
