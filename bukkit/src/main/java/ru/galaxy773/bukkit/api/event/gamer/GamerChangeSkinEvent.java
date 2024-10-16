package ru.galaxy773.bukkit.api.event.gamer;

import ru.galaxy773.bukkit.api.gamer.BukkitGamer;
import ru.galaxy773.multiplatform.impl.skin.Skin;

public class GamerChangeSkinEvent extends GamerEvent {

    private final Skin skin;

    public GamerChangeSkinEvent(BukkitGamer gamer, Skin skin) {
        super(gamer);
        this.skin = skin;
    }

    public Skin getSkin() {
        return this.skin;
    }
}
