package ru.galaxy773.lobby.parkour;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.bukkit.Location;
import org.bukkit.entity.Player;

public class ParkourWay {
    private final int id;
    private final Location startLocation;
    private final Location endLocation;
    private final Location topLocation;
    private final List<ParkourCheckpoint> checkpoints;
    private final int goldReward;
    private final int expReward;
    private final Map<String, Long> playersTime = new HashMap<>();
    private final Map<String, ParkourCheckpoint> playersCheckpoints = new HashMap<>();

    public void addPlayer(Player player) {
        this.playersTime.put(player.getName(), System.currentTimeMillis());
    }

    public void removePlayer(Player player) {
        this.playersTime.remove(player.getName());
        this.playersCheckpoints.remove(player.getName());
    }

    public boolean isInWay(Player player) {
        return this.playersTime.containsKey(player.getName());
    }

    public ParkourCheckpoint getCheckpoint(Player player) {
        return this.playersCheckpoints.get(player.getName());
    }

    public void setCheckpoint(Player player, ParkourCheckpoint parkourCheckpoint) {
        this.playersCheckpoints.put(player.getName(), parkourCheckpoint);
    }

    public long getTime(Player player) {
        return System.currentTimeMillis() - this.playersTime.get(player.getName());
    }

    public ParkourWay(int id, Location startLocation, Location endLocation, Location topLocation, List<ParkourCheckpoint> checkpoints, int goldReward, int expReward) {
        this.id = id;
        this.startLocation = startLocation;
        this.endLocation = endLocation;
        this.topLocation = topLocation;
        this.checkpoints = checkpoints;
        this.goldReward = goldReward;
        this.expReward = expReward;
    }

    public int getId() {
        return this.id;
    }

    public Location getStartLocation() {
        return this.startLocation;
    }

    public Location getEndLocation() {
        return this.endLocation;
    }

    public Location getTopLocation() {
        return this.topLocation;
    }

    public List<ParkourCheckpoint> getCheckpoints() {
        return this.checkpoints;
    }

    public int getGoldReward() {
        return this.goldReward;
    }

    public int getExpReward() {
        return this.expReward;
    }
}

