package ru.galaxy773.bukkit.api.tops.armorstand;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.bukkit.entity.Player;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
public final class StandPlayerStorage {

    private final Map<String, StandPlayer> players = new ConcurrentHashMap<>();

    void addPlayer(StandPlayer standPlayer) {
        players.put(standPlayer.getGamer().getName(), standPlayer);
    }

    void removePlayer(String name) {
        players.remove(name);
    }
    public StandPlayer getPlayer(Player gamer) {
        return players.get(gamer.getName());
    }
}
