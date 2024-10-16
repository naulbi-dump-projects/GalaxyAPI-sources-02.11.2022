package ru.galaxy773.bukkit.impl.entity.tracker;

import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.Set;

public interface TrackerEntity {

    int getEntityID();

    Location getLocation();
    Player getOwner();

    void spawn(Player player);
    void destroy(Player player);

    void removeTo(Player player);

    boolean canSee(Player player);
    boolean isHeadLook();

    void remove();

    void sendHeadRotation(Player player, float yaw, float pitch);

    Set<String> getHeadPlayers();
}
