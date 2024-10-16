package ru.galaxy773.bukkit.api.event.gamer;

import lombok.Getter;
import ru.galaxy773.bukkit.api.gamer.BukkitGamer;

@Getter
public class GamerLevelUpEvent extends GamerEvent {

    private final int level;

    public GamerLevelUpEvent(BukkitGamer gamer, int level) {
        super(gamer);
        this.level = level;
    }
}
