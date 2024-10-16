package ru.galaxy773.bukkit.api.inventory.type;

import gnu.trove.map.TIntObjectMap;
import ru.galaxy773.bukkit.api.inventory.GItem;
import ru.galaxy773.bukkit.api.utils.bukkit.InventoryUtil;

public interface GInventory extends BaseInventory {

    void setMutableSlot(int slot, boolean mutable);

    boolean isMutableSlot(int slot);

    void setItem(int slot, GItem item);
    
    default void setItem(GItem item, int x, int y) {
        setItem(InventoryUtil.getSlotByXY(x, y), item);
    }

    default void fill(GItem item) {
        for (int i = 0; i < size(); i++) {
            if (getItems().containsKey(i)) {
                continue;
            }
            setItem(i, item);
        }
    }

    void addItem(GItem GItem);

    String getName();

    void removeItem(int slot);

    void clearInventory();

    int size();

    TIntObjectMap<GItem> getItems();
}
