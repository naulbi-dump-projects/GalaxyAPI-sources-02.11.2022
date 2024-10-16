package ru.galaxy773.bukkit.api.event.gamer.async;

import ru.galaxy773.bukkit.api.event.gamer.GamerEvent;
import ru.galaxy773.bukkit.api.gamer.BukkitGamer;

public class AsyncGamerJoinEvent extends GamerEvent {

    public AsyncGamerJoinEvent(BukkitGamer gamer) {
        super(gamer, true);
    }
}
