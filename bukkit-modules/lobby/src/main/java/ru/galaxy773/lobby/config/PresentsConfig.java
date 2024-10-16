package ru.galaxy773.lobby.config;

import org.bukkit.Location;
import org.bukkit.block.Block;
import ru.galaxy773.bukkit.api.utils.bukkit.LocationUtil;
import ru.galaxy773.lobby.api.presents.PresentsManager;
import ru.galaxy773.lobby.presents.CraftPresentsManager;
import ru.galaxy773.lobby.presents.PresentsListener;

import java.util.List;
import java.util.stream.Collectors;

public class PresentsConfig extends LobbyConfig {

    private PresentsManager presentsManager;
    private List<Block> locations;
    private int rewardGold;
    private int rewardExp;

    public PresentsConfig() {
        super("presents.yml");
    }

    @Override
    public void load() {
        List<Location> locations = this.config.getStringList("locations").stream().map(location -> LocationUtil.stringToLocation(location, false)).collect(Collectors.toList());
        locations.forEach(location -> {
            if (!location.getWorld().isChunkLoaded(location.getBlockX(), location.getBlockZ()))
                location.getWorld().loadChunk(location.getBlockX(), location.getBlockZ());
        });
        this.locations = locations.stream().map(Location::getBlock).collect(Collectors.toList());
        this.rewardGold = this.config.getInt("reward_gold");
        this.rewardExp = this.config.getInt("reward_exp");
    }

    @Override
    public void init() {
        this.presentsManager = new CraftPresentsManager(JAVA_PLUGIN, this);
        new PresentsListener(JAVA_PLUGIN, this.presentsManager, this);
    }

    public PresentsManager getPresentsManager() {
        return this.presentsManager;
    }

    public List<Block> getLocations() {
        return this.locations;
    }

    public int getRewardGold() {
        return this.rewardGold;
    }

    public int getRewardExp() {
        return this.rewardExp;
    }
}

