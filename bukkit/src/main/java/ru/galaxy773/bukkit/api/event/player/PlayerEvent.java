package ru.galaxy773.bukkit.api.event.player;

import lombok.Getter;
import org.bukkit.entity.Player;
import ru.galaxy773.bukkit.api.event.GEvent;

@Getter
public abstract class PlayerEvent extends GEvent {

    private final Player player;

    public PlayerEvent(Player player) {
        super(false);
        this.player = player;
    }

    public PlayerEvent(Player player, boolean async) {
        super(async);
        this.player = player;
    }
}
