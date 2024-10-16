package ru.galaxy773.bukkit.api.event.player;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.entity.Player;
import ru.galaxy773.bukkit.api.hologram.Hologram;
import ru.galaxy773.bukkit.api.hologram.lines.ItemDropLine;

@Getter
public class PlayerPickupHoloEvent extends PlayerEvent {

    private final Hologram hologram;
    private final ItemDropLine line;
    @Setter
    private boolean remove;

    public PlayerPickupHoloEvent(Player player, Hologram hologram, ItemDropLine itemDropLine){
        super(player);
        this.hologram = hologram;
        this.line = itemDropLine;
    }
}
