package ru.galaxy773.bukkit.api.tops.armorstand;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.bukkit.Location;
import ru.galaxy773.bukkit.api.BukkitAPI;
import ru.galaxy773.bukkit.api.hologram.Hologram;
import ru.galaxy773.bukkit.api.hologram.HologramAPI;

import java.util.List;

@RequiredArgsConstructor(access = AccessLevel.PUBLIC)
public class Top {

    protected static final HologramAPI HOLOGRAM_API = BukkitAPI.getHologramAPI();
    protected final TopManager standTopManager;
    protected final Location location;
    protected final Hologram hologram;

    public final Hologram getHologramMiddle() {
        return hologram;
    }

    public final List<StandTop> getStands() {
        return standTopManager.getAllStands(this);
    }

    public final Location getLocation() {
        return location.clone();
    }

    public final int getId() {
        int id = 0;
        for (Top topType : standTopManager.getTops()) {
            if (topType == this) {
                break;
            }
            id++;
        }
        return id;
    }
}
