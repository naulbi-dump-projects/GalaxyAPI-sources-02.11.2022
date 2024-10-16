package ru.galaxy773.bukkit.api.event.player;

import lombok.Getter;
import org.bukkit.entity.Player;
import ru.galaxy773.bukkit.api.hologram.Hologram;

@Getter
public class PlayerInteractHologramEvent extends PlayerEvent {

    private final Hologram hologram;
    private final PlayerInteractCustomStandEvent.Action action;

    public PlayerInteractHologramEvent(Player player, Hologram hologram,
                                      PlayerInteractCustomStandEvent.Action action) {
        super(player);
        this.hologram = hologram;
        this.action = action;
    }
}
