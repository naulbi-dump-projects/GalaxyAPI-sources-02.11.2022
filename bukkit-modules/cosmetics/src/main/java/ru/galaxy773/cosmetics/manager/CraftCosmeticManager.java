package ru.galaxy773.cosmetics.manager;

import org.bukkit.entity.Player;
import ru.galaxy773.bukkit.api.gamer.BukkitGamer;
import ru.galaxy773.bukkit.api.gamer.GamerManager;
import ru.galaxy773.cosmetics.api.manager.CosmeticManager;
import ru.galaxy773.cosmetics.api.player.CosmeticPlayer;
import ru.galaxy773.cosmetics.sql.CosmeticLoader;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class CraftCosmeticManager implements CosmeticManager {

    private final Map<String, CosmeticPlayer> players = new ConcurrentHashMap<>();

    @Override
    public CosmeticPlayer getCosmeticPlayer(Player player) {
        return this.players.get(player.getName());
    }

    @Override
    public void loadPlayer(Player player) {
        BukkitGamer gamer = GamerManager.getGamer(player.getName());
        this.players.put(player.getName(), CosmeticLoader.getCosmeticPlayer(player, gamer.getPlayerID()));
    }

    @Override
    public void unloadPlayer(Player player) {
        this.players.remove(player.getName());
    }
}

