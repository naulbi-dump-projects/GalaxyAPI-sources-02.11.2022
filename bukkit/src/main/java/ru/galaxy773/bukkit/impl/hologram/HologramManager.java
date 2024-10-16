package ru.galaxy773.bukkit.impl.hologram;

import io.netty.util.internal.ConcurrentSet;
import org.bukkit.Bukkit;
import ru.galaxy773.bukkit.api.entity.stand.CustomStand;
import ru.galaxy773.bukkit.api.hologram.Hologram;
import ru.galaxy773.bukkit.impl.hologram.lines.CraftHoloLine;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class HologramManager {

    private final Set<CraftHologram> holograms = new ConcurrentSet<>();
    private final Map<CustomStand, Hologram> hologramsByStand = new ConcurrentHashMap<>();

    void addHologram(CraftHologram hologram) {
        holograms.add(hologram);
    }

    public void addCustomStand(Hologram hologram, CustomStand customStand) {
        hologramsByStand.put(customStand, hologram);
    }

    public void removeCustomStand(CustomStand customStand) {
        hologramsByStand.remove(customStand);
    }

    Set<CraftHologram> getHolograms() {
        return holograms;
    }

    Map<CustomStand, Hologram> getHologramByStand() {
        return hologramsByStand;
    }

    void removeHologram(CraftHologram hologram) {
        holograms.remove(hologram);
        for (CraftHoloLine craftHoloLine : hologram.getLines()){
            Bukkit.getOnlinePlayers().forEach(craftHoloLine::hideTo);
            craftHoloLine.remove();
        }
    }
}
