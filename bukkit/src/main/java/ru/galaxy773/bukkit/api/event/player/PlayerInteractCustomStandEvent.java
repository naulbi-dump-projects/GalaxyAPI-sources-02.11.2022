package ru.galaxy773.bukkit.api.event.player;

import lombok.Getter;
import org.bukkit.entity.Player;
import ru.galaxy773.bukkit.api.entity.stand.CustomStand;

@Getter
public class PlayerInteractCustomStandEvent extends PlayerEvent {

    private final CustomStand stand;
    private final Action action;

    public PlayerInteractCustomStandEvent(Player player, CustomStand stand, Action action) {
        super(player);
        this.stand = stand;
        this.action = action;
    }

    public enum Action {
        LEFT_CLICK, RIGHT_CLICK
    }
}
