package ru.galaxy773.bukkit.api.event.gamer;

import lombok.Getter;
import ru.galaxy773.bukkit.api.event.GEvent;
import ru.galaxy773.bukkit.api.gamer.BukkitGamer;

@Getter
public abstract class GamerEvent extends GEvent {

    private final BukkitGamer gamer;

    public GamerEvent(BukkitGamer gamer) {
        super(false);
        this.gamer = gamer;
    }

    public GamerEvent(BukkitGamer gamer, boolean async) {
        super(async);
        this.gamer = gamer;
    }
}
