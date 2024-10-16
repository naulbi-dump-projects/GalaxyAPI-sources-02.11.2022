package ru.galaxy773.lobby.presents;

import gnu.trove.list.TLongList;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import ru.galaxy773.bukkit.api.BukkitAPI;
import ru.galaxy773.bukkit.api.effect.ParticleAPI;
import ru.galaxy773.bukkit.api.effect.ParticleEffect;
import ru.galaxy773.bukkit.api.gamer.BukkitGamer;
import ru.galaxy773.bukkit.api.gamer.GamerManager;
import ru.galaxy773.bukkit.api.utils.bukkit.LocationUtil;
import ru.galaxy773.lobby.Lobby;
import ru.galaxy773.lobby.api.presents.PresentsManager;
import ru.galaxy773.lobby.config.PresentsConfig;

public class CraftPresentsManager implements PresentsManager {

    private final ParticleAPI particleAPI = BukkitAPI.getParticleAPI();
    private final PresentsConfig presentsConfig;
    private final Map<String, TLongList> cachedStore = new ConcurrentHashMap<String, TLongList>();

    public CraftPresentsManager(Lobby javaPlugin, PresentsConfig presentsConfig) {
        this.presentsConfig = presentsConfig;
        Bukkit.getScheduler().runTaskTimerAsynchronously(javaPlugin, () ->
                this.presentsConfig.getLocations()
                        .forEach(block -> this.particleAPI.sendEffect(
                                ParticleEffect.VILLAGER_HAPPY,
                                Bukkit.getOnlinePlayers().parallelStream().filter(player -> this.cachedStore.get(player.getName()) != null && !this.cachedStore.get(player.getName()).contains(LocationUtil.convert(block.getLocation()))).collect(Collectors.toList()),
                                block.getLocation().clone().add(0.5, 0.5, 0.5),
                                0.2f,
                                0.2f,
                                0.2f,
                                0.1f,
                                8)
                        ), 0L, 5L);
    }

    @Override
    public int getMaxCount() {
        return this.presentsConfig.getLocations().size();
    }

    @Override
    public int getFindedCount(Player player) {
        return this.cachedStore.containsKey(player.getName()) ? this.cachedStore.get(player.getName()).size() : 0;
    }

    @Override
    public boolean locationExists(Block block) {
        return this.presentsConfig.getLocations().contains(block);
    }

    @Override
    public boolean locationFind(Player player, Location location) {
        long longLocation = LocationUtil.convert(location);
        String playerName = player.getName();
        if (this.cachedStore.containsKey(playerName) && this.cachedStore.get(playerName).contains(longLocation)) {
            return false;
        }
        if (!this.cachedStore.containsKey(playerName)) {
            return false;
        }
        this.cachedStore.get(playerName).add(longLocation);
        BukkitGamer gamer = GamerManager.getGamer((Player)player);
        PresentsLoader.addToStore(gamer.getPlayerID(), longLocation);
        return true;
    }

    @Override
    public void loadPlayer(Player player) {
        BukkitGamer gamer = GamerManager.getGamer((Player)player);
        this.cachedStore.put(player.getName(), PresentsLoader.getStore(gamer.getPlayerID()));
    }

    @Override
    public void unloadPlayer(Player player) {
        this.cachedStore.remove(player.getName());
    }
}

