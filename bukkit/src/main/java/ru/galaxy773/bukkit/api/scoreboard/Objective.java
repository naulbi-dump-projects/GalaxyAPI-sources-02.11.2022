package ru.galaxy773.bukkit.api.scoreboard;

import org.bukkit.entity.Player;
import ru.galaxy773.bukkit.api.entity.depend.PacketObject;

import java.util.Map;

public interface Objective extends PacketObject {

    void setDisplayName(String displayName);

    void setDisplaySlot(DisplaySlot displaySlot);

    void setScore(Player player, int score);

    Map<String, Integer> getScores();

    void removeScore(String name);
    void removeScore(Player player);
}
