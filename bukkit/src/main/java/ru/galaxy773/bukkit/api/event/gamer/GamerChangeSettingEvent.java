package ru.galaxy773.bukkit.api.event.gamer;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.event.Cancellable;
import ru.galaxy773.bukkit.api.gamer.BukkitGamer;
import ru.galaxy773.multiplatform.api.gamer.constants.SettingsType;

@Getter
public class GamerChangeSettingEvent extends GamerEvent implements Cancellable {

    private final SettingsType setting;
    private final boolean enable;
    @Setter
    private boolean cancelled;

    public GamerChangeSettingEvent(BukkitGamer gamer, SettingsType setting, boolean enable) {
        super(gamer);
        this.setting = setting;
        this.enable = enable;
    }
}
