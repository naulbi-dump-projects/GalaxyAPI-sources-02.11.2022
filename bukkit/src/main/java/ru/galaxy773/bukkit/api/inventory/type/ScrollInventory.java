package ru.galaxy773.bukkit.api.inventory.type;

import ru.galaxy773.bukkit.api.inventory.GItem;
import ru.galaxy773.bukkit.api.utils.bukkit.InventoryUtil;

import java.util.List;

public interface ScrollInventory extends BaseInventory {

    void addItemScroll(GItem item);
    void addItemsScroll(List<GItem> items);

    void removeItemScroll(int numberItem);

    void removeItemsScroll();

    void clearInventory();

    String getName();

    void setMutableSlot(int slot, boolean mutable);

    boolean isMutableSlot(int slot);

    void setItem(int slot, GItem item);

    default void setItem(GItem item, int x, int y) {
        setItem(InventoryUtil.getSlotByXY(x, y), item);
    }

}
