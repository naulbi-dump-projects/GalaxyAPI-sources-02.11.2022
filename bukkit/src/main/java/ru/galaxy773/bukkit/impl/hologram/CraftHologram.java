package ru.galaxy773.bukkit.impl.hologram;

import lombok.Getter;
import lombok.NonNull;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;
import ru.galaxy773.bukkit.api.BukkitAPI;
import ru.galaxy773.bukkit.api.entity.EntityAPI;
import ru.galaxy773.bukkit.api.hologram.HoloLine;
import ru.galaxy773.bukkit.api.hologram.Hologram;
import ru.galaxy773.bukkit.api.hologram.lines.AnimationHoloLine;
import ru.galaxy773.bukkit.api.hologram.lines.ItemDropLine;
import ru.galaxy773.bukkit.api.hologram.lines.ItemFloatingLine;
import ru.galaxy773.bukkit.api.hologram.lines.TextHoloLine;
import ru.galaxy773.bukkit.api.utils.bukkit.WorldUtil;
import ru.galaxy773.bukkit.impl.hologram.lines.CraftHoloLine;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.function.Supplier;

@SuppressWarnings("unchecked")
public class CraftHologram implements Hologram {

	private static final EntityAPI ENTITY_API = BukkitAPI.getEntityAPI();
    @Getter
    private final HologramManager hologramManager;
    @Getter
    private final JavaPlugin plugin;
    private Player owner = null;
    private Location location;
    private final List<CraftHoloLine> lines = Collections.synchronizedList(new ArrayList<>());
    private boolean visionAll = false;
    private final CraftHologramDepend depend;
    
    CraftHologram(JavaPlugin javaPlugin, HologramManager hologramManager, @NonNull Location location) {
        WorldUtil.checkAndLoadChunk(location.getWorld(), location.getBlockX(), location.getBlockZ());
    	this.plugin = javaPlugin;
        this.hologramManager = hologramManager;
        this.location = location.clone().subtract(0, 0.5, 0);
        this.depend = new CraftHologramDepend(this);
        hologramManager.addHologram(this);
    }

    @Override
    public boolean isVisibleTo(Player player) {
        return !lines.isEmpty() && lines.get(0).isVisibleTo(player);
    }

    @Override
    public Collection<String> getVisiblePlayers() {
        List<String> players = new ArrayList<>();

        if (visionAll)
            Bukkit.getOnlinePlayers().forEach(player -> players.add(player.getName()));
        else
            return depend.playersVision;

        return players;
    }

    @Override
    public void showTo(Player player) {
        if (player == null || !player.isOnline())
            return;
        if (isVisibleTo(player))
            return;
        depend.playersVision.add(player.getName());
        lines.forEach(holoLine -> holoLine.showTo(player));
    }

    @Override
    public void removeTo(Player player) {
    	if (player == null || !player.isOnline()) {
            return;
    	}
        if (!getVisiblePlayers().remove(player.getName()))
            return;

        lines.forEach(holoLine -> holoLine.hideTo(player));
    }

    @Override
    public void hideAll() {
        setPublic(false);
        Bukkit.getOnlinePlayers().forEach(this::removeTo);
    }

    @Override
    public Location getLocation() {
        return location.clone();
    }

    public List<CraftHoloLine> getLines() {
        return lines;
    }

    @Override
    public boolean isPublic() {
        return visionAll;
    }

    @Override
    public void setPublic(boolean vision) {
        visionAll = vision;
        lines.forEach(holoLine -> holoLine.setPublic(vision));
    }

    @Override
    public void onTeleport(Location location) {
        this.location = location.clone().subtract(0, 0.5, 0);
        depend.updateLines();
    }

    @Override
    public AnimationHoloLine addAnimationLine(long speed, Supplier<String> replacerLine) {
        return depend.setAnimationLine(lines.size(), speed, replacerLine);
    }

    @Override
    public TextHoloLine addTextLine(String text) {
        return depend.setTextLine(lines.size(), text);
    }

    @Override
    public void addTextLine(Collection<String> listText) {
        listText.forEach(this::addTextLine);
    }

    @Override
    public ItemDropLine addDropLine(boolean pickup, ItemStack item) {
        return depend.setItemDropLine(lines.size(), pickup, item);
    }

    @Override
    public ItemFloatingLine addBigItemLine(boolean rotate, ItemStack item) {
        return depend.setItemFloatingLine(rotate, lines.size(), item);
    }

    @Override
    public TextHoloLine insertTextLine(int index, String text) {
        TextHoloLine textHoloLine = depend.setTextLine(index, text);
        depend.updateLines();
        return textHoloLine;
    }

    @Override
    public ItemDropLine insertDropLine(int index, boolean pickup, ItemStack item) {
        ItemDropLine itemDropLine = depend.setItemDropLine(index, pickup, item);
        depend.updateLines();
        return itemDropLine;
    }

    @Override
    public ItemFloatingLine insertBigItemLine(int index, boolean rotate, ItemStack item) {
        ItemFloatingLine itemFloatingLine = depend.setItemFloatingLine(rotate, index, item);
        depend.updateLines();
        return itemFloatingLine;
    }

    @Override
    public List<HoloLine> getHoloLines() {
        return new ArrayList<>(lines);
    }

    @Override
    public <T extends HoloLine> T getHoloLine(int index) {
        if (getLines().size() <= index)
            return null;
        return (T) getLines().get(index);
    }

    @Override
    public int getSize() {
        return lines.size();
    }

    @Override
    public void removeLine(int index) {
        if (lines.isEmpty())
            return;

        if (index >= 0 && index < lines.size()) {
            CraftHoloLine craftHoloLine = lines.get(index);
            lines.remove(craftHoloLine);
            craftHoloLine.remove();
            depend.updateLines();
        }
    }

    @Override
    public Player getOwner() {
        return owner;
    }

    @Override
    public void setOwner(Player owner) {
        this.owner = owner;
        showTo(owner);
    }

    @Override
    public void removeLine(HoloLine line) {
        CraftHoloLine craftHoloLine = (CraftHoloLine) line;
        if (lines.isEmpty() || !lines.contains(craftHoloLine))
            return;

        lines.remove(craftHoloLine);
        craftHoloLine.remove();
        depend.updateLines();
    }

    @Override
    public void remove() {
        hologramManager.removeHologram(this);
    }
}
