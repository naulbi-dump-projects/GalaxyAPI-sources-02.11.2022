package ru.galaxy773.bukkit.api.event.gamer.async;

import lombok.Getter;
import lombok.Setter;
import ru.galaxy773.bukkit.api.event.gamer.GamerEvent;
import ru.galaxy773.bukkit.api.gamer.BukkitGamer;
import ru.galaxy773.multiplatform.impl.gamer.sections.Section;

import java.util.HashSet;
import java.util.Set;

public class AsyncGamerLoadSectionEvent extends GamerEvent {

    @Getter
    @Setter
    private Set<Class<? extends Section>> sections;

    public AsyncGamerLoadSectionEvent(BukkitGamer gamer) {
        super(gamer, true);
        this.sections = new HashSet<>();
    }
}
