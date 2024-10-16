package ru.galaxy773.bukkit.impl.inventory.inventories;

import gnu.trove.TCollections;
import gnu.trove.map.TIntObjectMap;
import gnu.trove.map.hash.TIntObjectHashMap;
import gnu.trove.set.TIntSet;
import gnu.trove.set.hash.TIntHashSet;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import ru.galaxy773.bukkit.api.gamer.BukkitGamer;
import ru.galaxy773.bukkit.api.inventory.GItem;
import ru.galaxy773.bukkit.api.inventory.type.GInventory;
import ru.galaxy773.bukkit.impl.inventory.GuiManagerListener;

import java.util.function.Consumer;

public class CraftGInventory implements GInventory {

    private final GuiManagerListener guiManagerListener;
    @Getter
    private final Inventory handle;
    @Getter
    private final String name;
    private final int rows;
    @Getter
    private final TIntObjectMap<GItem> items = TCollections.synchronizedMap(new TIntObjectHashMap<>());
    private final TIntSet mutableslots = new TIntHashSet();
    @Getter
    @Setter
    private Consumer<Player> openAction;
    @Getter
    @Setter
    private Consumer<Player> closeAction;

    public CraftGInventory(String name, int rows, GuiManagerListener guiManagerListener) {
        this.guiManagerListener = guiManagerListener;

        this.name = name;
        this.rows = rows;

        this.handle = Bukkit.createInventory(this, 9 * rows, name);
    }

    @Override
    public void setMutableSlot(int slot, boolean mutable) {
        if (mutable)
            this.mutableslots.add(slot);
        else
            this.mutableslots.remove(slot);
    }

    @Override
    public boolean isMutableSlot(int slot) {
        return this.mutableslots.contains(slot);
    }

    @Override
    public void setItem(int slot, GItem item) {
        ItemStack stack = item.getItem();
        this.items.put(slot, item);
        this.handle.setItem(slot, stack);
    }

    @Override
    public void addItem(GItem GItem) {
        for (int slot = 0; slot < this.handle.getSize(); slot++) {
            if (this.handle.getItem(slot) == null) {
                setItem(slot, GItem);
                return;
            }
        }
    }

    @Override
    public void removeItem(int slot) {
        GItem item = this.items.remove(slot);
        if (item == null) return;
        this.handle.setItem(slot, null);
    }

    @Override
    public void openInventory(BukkitGamer gamer) {
        this.openInventory(gamer.getPlayer());
    }

    @Override
    public void openInventory(Player player) {
        if (player == null || !player.isOnline())
            return;

        this.guiManagerListener.openInventory(player, this);
    }

    @Override
    public void clearInventory() {
        this.items.clear();
        this.handle.clear();
    }

    @Override
    public int size() {
        return rows * 9;
    }
}
