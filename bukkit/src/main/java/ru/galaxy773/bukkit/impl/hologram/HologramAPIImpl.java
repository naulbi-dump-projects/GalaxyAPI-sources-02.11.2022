package ru.galaxy773.bukkit.impl.hologram;

import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.plugin.java.JavaPlugin;
import ru.galaxy773.bukkit.api.BukkitPlugin;
import ru.galaxy773.bukkit.api.hologram.Hologram;
import ru.galaxy773.bukkit.api.hologram.HologramAPI;

import java.util.ArrayList;
import java.util.List;

@Getter
public class HologramAPIImpl implements HologramAPI {

    private final HologramManager hologramManager;

    public HologramAPIImpl() {
        this.hologramManager = new HologramManager();
        new HologramListener(this);
        new HologramAPIListener(hologramManager);
        Bukkit.getScheduler().runTaskTimerAsynchronously(BukkitPlugin.getInstance(), new HologramTask(hologramManager), 0, 1L);
    }

    @Override
    public Hologram createHologram(JavaPlugin javaPlugin, Location location) {
        return new CraftHologram(javaPlugin, hologramManager, location);
    }

    @Override
    public List<Hologram> getHolograms() {
        return new ArrayList<>(hologramManager.getHolograms());
    }
}
