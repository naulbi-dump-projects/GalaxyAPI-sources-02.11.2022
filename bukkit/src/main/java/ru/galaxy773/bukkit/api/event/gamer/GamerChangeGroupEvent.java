package ru.galaxy773.bukkit.api.event.gamer;

import lombok.Getter;
import ru.galaxy773.bukkit.api.gamer.BukkitGamer;
import ru.galaxy773.multiplatform.api.gamer.constants.Group;

@Getter
public class GamerChangeGroupEvent extends GamerEvent {

    private final Group group;

    public GamerChangeGroupEvent(BukkitGamer gamer, Group group) {
        super(gamer);
        this.group = group;
    }
}
