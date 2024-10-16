package ru.galaxy773.bukkit.api.game;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import ru.galaxy773.bukkit.api.utils.bukkit.Head;

import java.util.Arrays;

@AllArgsConstructor
public enum GameType {

    AUTH("Auth", "auth", Head.LOBBY.getItemStack()),
    HUB("Hub", "hub", Head.HUB_ON.getItemStack()),
    LOBBY("Lobby", "lobby", Head.LOBBY.getItemStack()),
    SURVIVAL("Survival", "survival", Head.SURVIVAL.getItemStack()),
    ONEBLOCK("OneBlock", "oneblock", Head.ONEBLOCK.getItemStack()),
    LW("LuckyWars", "lw-lobby", Head.LUCKYWARS.getItemStack()),
    BW("BedWars", "bw-lobby", new ItemStack(Material.BED, 1, (short) 14)),
    SW("SkyWars", "sw-lobby", new ItemStack(Material.ENDER_PEARL));

    @Getter
    @Setter
    private static GameType current;

    @Getter
    private final String name;
    @Getter
    private final String lobbyChannel;
    private final ItemStack icon;

    public ItemStack getIcon() {
        return this.icon.clone();
    }

    public static GameType tryGet(String gameType) {
        gameType = gameType.toUpperCase();
        String finalGameType = gameType;
        return Arrays.stream(values())
                .filter(gameType1 -> gameType1.name().equals(finalGameType))
                .findFirst()
                .orElse(null);
    }
}
