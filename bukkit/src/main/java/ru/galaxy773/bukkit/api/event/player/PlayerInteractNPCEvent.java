package ru.galaxy773.bukkit.api.event.player;

import lombok.Getter;
import org.bukkit.entity.Player;
import ru.galaxy773.bukkit.api.entity.depend.ClickType;
import ru.galaxy773.bukkit.api.entity.npc.NPC;

@Getter
public class PlayerInteractNPCEvent extends PlayerEvent {

    private final NPC npc;
    private final ClickType clickType;

    public PlayerInteractNPCEvent(Player player, NPC npc, ClickType clickType){
        super(player);
        this.npc = npc;
        this.clickType = clickType;
    }

    public enum Action {
        LEFT_CLICK, RIGHT_CLICK
    }
}
