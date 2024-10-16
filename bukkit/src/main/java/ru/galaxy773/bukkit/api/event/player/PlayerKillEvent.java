package ru.galaxy773.bukkit.api.event.player;

import lombok.Getter;
import org.bukkit.entity.Player;
import ru.galaxy773.bukkit.api.event.GEvent;

@Getter
public final class PlayerKillEvent extends GEvent {

	private final Player player;
	private final Player killer;
	
	public PlayerKillEvent(Player player, Player killer) {
		super(false);
		this.player = player;
		this.killer = killer;
	}
}
