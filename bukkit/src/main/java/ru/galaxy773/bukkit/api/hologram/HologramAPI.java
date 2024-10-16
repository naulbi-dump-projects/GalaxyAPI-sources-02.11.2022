package ru.galaxy773.bukkit.api.hologram;

import org.bukkit.Location;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.List;

public interface HologramAPI {

    Hologram createHologram(JavaPlugin javaPlugin, Location location);

    List<Hologram> getHolograms();
}
