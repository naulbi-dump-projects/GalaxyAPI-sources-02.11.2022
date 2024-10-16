package ru.galaxy773.bukkit.impl.hologram.lines;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import ru.galaxy773.bukkit.api.BukkitAPI;
import ru.galaxy773.bukkit.api.entity.EntityAPI;
import ru.galaxy773.bukkit.api.entity.stand.CustomStand;
import ru.galaxy773.bukkit.api.hologram.HoloLine;
import ru.galaxy773.bukkit.api.hologram.Hologram;
import ru.galaxy773.bukkit.impl.hologram.CraftHologram;
import ru.galaxy773.bukkit.impl.hologram.HologramManager;

public abstract class CraftHoloLine implements HoloLine {

    private static final EntityAPI ENTITY_API = BukkitAPI.getEntityAPI();

    private final HologramManager hologramManager;
    protected final Hologram hologram;
    protected final CustomStand customStand;

    CraftHoloLine(CraftHologram hologram, Location location) {
        this.hologram = hologram;
        this.hologramManager = hologram.getHologramManager();

        customStand = ENTITY_API.createStand(hologram.getPlugin(), location);
        customStand.setInvisible(true);
        customStand.setSmall(true);
        customStand.setBasePlate(false);

        hologramManager.addCustomStand(hologram, customStand);
    }

    public void remove() {
        hologramManager.removeCustomStand(customStand);
        customStand.remove();
    }

    @Override
    public CustomStand getCustomStand() {
        return customStand;
    }

    @Override
    public void delete() {
        hologram.removeLine(this);
    }

    public void teleport(Location location){
        customStand.onTeleport(location);
    }

    public void showTo(Player player) {
        customStand.showTo(player);
    }

    public void hideTo(Player player) {
        customStand.removeTo(player);
    }

    public void setPublic(boolean vision) {
        customStand.setPublic(vision);
    }

    public boolean isVisibleTo(Player player) {
        return customStand.isVisibleTo(player);
    }

}
