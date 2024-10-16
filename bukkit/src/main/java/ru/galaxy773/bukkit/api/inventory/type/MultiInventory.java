package ru.galaxy773.bukkit.api.inventory.type;

import org.bukkit.entity.Player;
import ru.galaxy773.bukkit.api.gamer.BukkitGamer;
import ru.galaxy773.bukkit.api.inventory.GItem;
import ru.galaxy773.bukkit.api.utils.bukkit.InventoryUtil;

import java.util.List;

public interface MultiInventory extends BaseInventory {

    default void openInventory(BukkitGamer gamer, int page) {
        openInventory(gamer.getPlayer(), page);
    }

    void openInventory(Player player, int page);

    void setMutableSlot(int page, int slot, boolean mutable);

    boolean isMutableSlot(int page, int slot);

    void setItem(int page, int slot, GItem item);

    default void setItem(GItem item, int x, int y) {
        setItem(InventoryUtil.getSlotByXY(x, y), item);
    }

    default void fill(GItem item) {
        for (GInventory inventory : getInventories()) {
            inventory.fill(item);
        }
    }

    void addItem(int page, GItem GItem);

    void setItem(int slot, GItem item);

    void removeItem(int page, int slot);

    void removePage(int page);

    void clearInventories();

    String getName();

    int size();

    int pages();

    List<GInventory> getInventories();
}
