package ru.galaxy773.bukkit.api.event.gamer;

import org.bukkit.event.Cancellable;
import ru.galaxy773.bukkit.api.gamer.BukkitGamer;

public class GamerSkinApplyEvent extends GamerEvent implements Cancellable {

    private boolean cancelled;

    public GamerSkinApplyEvent(BukkitGamer gamer) {
        super(gamer);
    }

    public void setCancelled(boolean cancelled) {
        this.cancelled = cancelled;
    }

    public boolean isCancelled() {
        return this.cancelled;
    }
}

