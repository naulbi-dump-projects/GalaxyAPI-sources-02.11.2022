package ru.galaxy773.bukkit.api.event.gamer.async;

import org.bukkit.event.Cancellable;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;
import ru.galaxy773.bukkit.api.event.gamer.GamerEvent;
import ru.galaxy773.bukkit.api.gamer.BukkitGamer;

public class AsyncGamerPreLoginEvent extends GamerEvent implements Cancellable {

    private final AsyncPlayerPreLoginEvent event;
    private boolean cancelled;

    public AsyncGamerPreLoginEvent(BukkitGamer gamer, AsyncPlayerPreLoginEvent event) {
        super(gamer, true);
        this.event = event;
    }

    public void setCancelled(boolean cancelled) {
        this.cancelled = cancelled;
    }

    public AsyncPlayerPreLoginEvent getEvent() {
        return this.event;
    }

    public boolean isCancelled() {
        return this.cancelled;
    }
}
