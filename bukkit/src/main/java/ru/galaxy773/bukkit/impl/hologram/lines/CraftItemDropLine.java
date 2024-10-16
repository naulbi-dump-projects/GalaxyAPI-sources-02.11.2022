package ru.galaxy773.bukkit.impl.hologram.lines;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import ru.galaxy773.bukkit.api.event.player.PlayerPickupHoloEvent;
import ru.galaxy773.bukkit.api.hologram.Hologram;
import ru.galaxy773.bukkit.api.hologram.lines.ItemDropLine;
import ru.galaxy773.bukkit.api.utils.bukkit.BukkitUtil;
import ru.galaxy773.bukkit.api.utils.bukkit.LocationUtil;
import ru.galaxy773.bukkit.impl.hologram.CraftHologram;

@Getter
public class CraftItemDropLine extends CraftHoloLine implements ItemDropLine {

    private ItemStack itemStack;
    @Setter
    private boolean pickup;

    public CraftItemDropLine(CraftHologram hologram, Location location, boolean pickup, ItemStack item) {
        super(hologram, location);
        this.pickup = pickup;
        this.itemStack = item;
        this.setItem(itemStack);
    }

    @Override
    public ItemStack getItemStack() {
        return itemStack.clone();
    }

    @Override
    public void setItem(ItemStack item) {
        customStand.setItemPassenger(item);
    }

    public void checkPickup(Player player, Hologram hologram) {
        if (!(hologram.isVisibleTo(player) || hologram.isPublic())) {
            return;
        }

        double range = LocationUtil.distance(player.getLocation(), customStand.getLocation());
        if (range < 1.5 && range != -1) {
            PlayerPickupHoloEvent event = new PlayerPickupHoloEvent(player, hologram, this);
            BukkitUtil.runTask(() -> BukkitUtil.callEvent(event));
            if (event.isRemove()) {
                delete();
            }
        }
    }
}
