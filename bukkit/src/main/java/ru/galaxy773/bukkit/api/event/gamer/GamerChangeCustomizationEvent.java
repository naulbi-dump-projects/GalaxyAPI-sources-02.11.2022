package ru.galaxy773.bukkit.api.event.gamer;

import lombok.Getter;
import ru.galaxy773.bukkit.api.gamer.BukkitGamer;
import ru.galaxy773.multiplatform.api.gamer.data.CustomizationType;

@Getter
public class GamerChangeCustomizationEvent extends GamerEvent {

    private final CustomizationType customizationType;

    public GamerChangeCustomizationEvent(BukkitGamer gamer, CustomizationType customizationType) {
        super(gamer);
        this.customizationType = customizationType;
    }
}
