package ru.galaxy773.bukkit.nms.scoreboard;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class GScore {

    private final String name;
    private final GObjective gObjective;
    private int score;

    @Override
    public String toString() {
        return "GScore{name=" + name + ", score=" + score +"}";
    }
}
