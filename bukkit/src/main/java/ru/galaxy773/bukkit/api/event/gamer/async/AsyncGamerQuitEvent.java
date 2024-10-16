package ru.galaxy773.bukkit.api.event.gamer.async;

import ru.galaxy773.bukkit.api.event.gamer.GamerEvent;
import ru.galaxy773.bukkit.api.gamer.BukkitGamer;

public class AsyncGamerQuitEvent extends GamerEvent {

    public AsyncGamerQuitEvent(BukkitGamer gamer) {
        super(gamer, true);
    }
}
