package ru.galaxy773.bukkit.api.event.player;

import lombok.Getter;
import org.bukkit.entity.Player;

@Getter
public class AsyncChunkSendEvent extends PlayerEvent {

    private final String worldName;
    private final int x;
    private final int z;

    public AsyncChunkSendEvent(Player player, String worldName, int x, int z) {
        super(player, true);
        this.worldName = worldName;
        this.x = x;
        this.z = z;
    }
}