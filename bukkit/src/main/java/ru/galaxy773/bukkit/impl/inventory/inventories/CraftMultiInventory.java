package ru.galaxy773.bukkit.impl.inventory.inventories;

import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import ru.galaxy773.bukkit.api.BukkitAPI;
import ru.galaxy773.bukkit.api.gamer.BukkitGamer;
import ru.galaxy773.bukkit.api.inventory.GItem;
import ru.galaxy773.bukkit.api.inventory.InventoryAPI;
import ru.galaxy773.bukkit.api.inventory.type.GInventory;
import ru.galaxy773.bukkit.api.inventory.type.MultiInventory;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class CraftMultiInventory implements MultiInventory {
	
    private static final InventoryAPI API = BukkitAPI.getInventoryAPI();

    private GInventory lastUsed;
    private final List<GInventory> inventories = new ArrayList<>();
    private final Player player;
    private final int rows;
    private final String name;

    public CraftMultiInventory(Player player, String name, int rows) {
        this.player = player;
        this.name = name;
        this.rows = rows;
        GInventory inventory = API.createInventory(player, name, rows);
        this.inventories.add(inventory);
    }

    @Override
    public void openInventory(Player player, int page) {
        GInventory inventory = this.inventories.get(page);
        this.lastUsed = inventory;
        if (inventory == null)
            inventory = this.inventories.get(0);

        inventory.openInventory(player);
    }

    @Override
    public void setMutableSlot(int page, int slot, boolean mutable) {
        GInventory inventory = this.inventories.get(page);
        if (inventory == null)
            return;

        inventory.setMutableSlot(slot, mutable);
    }

    @Override
    public boolean isMutableSlot(int page, int slot) {
        GInventory inventory = this.inventories.get(page);
        if (inventory == null)
            return false;

        return inventory.isMutableSlot(slot);
    }

    @Override
    public void openInventory(BukkitGamer gamer) {
        openInventory(gamer.getPlayer());
    }

    @Override
    public void openInventory(Player player) {
        this.inventories.get(0).openInventory(player);
    }

    @Override
    public void setOpenAction(Consumer<Player> openAction) {
        this.inventories.forEach(inventory -> inventory.setOpenAction(openAction));
    }

    @Override
    public void setCloseAction(Consumer<Player> closeAction) {
        this.inventories.forEach(inventory -> inventory.setCloseAction(closeAction));
    }

    @Override
    public Consumer<Player> getOpenAction() {
        return this.inventories.get(0).getOpenAction();
    }

    @Override
    public Consumer<Player> getCloseAction() {
        return this.inventories.get(0).getCloseAction();
    }

    @Override
    public Inventory getHandle() {
        return this.lastUsed.getHandle();
    }

    @Override
    public void setItem(int page, int slot, GItem item) {
        createPages(page);

        GInventory inventory = this.inventories.get(page);
        if (inventory == null)
            return;

        inventory.setItem(slot, item);
    }

    @Override
    public void addItem(int page, GItem item) {
        createPages(page);

        GInventory inventory = this.inventories.get(page);
        inventory.addItem(item);
    }

    @Override
    public void setItem(int slot, GItem item) {
        this.inventories.forEach(inv -> inv.setItem(slot, item));
    }

    @Override
    public void removeItem(int page, int slot) {
        if (page > this.inventories.size())
            return;

        GInventory inventory = this.inventories.get(page);
        inventory.removeItem(slot);
    }

    @Override
    public void removePage(int page) {
        this.inventories.remove(page);
    }

    @Override
    public void clearInventories() {
        this.inventories.forEach(GInventory::clearInventory);
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public int size() {
        return this.rows * 9;
    }

    @Override
    public int pages() {
        return this.inventories.size();
    }

    @Override
    public List<GInventory> getInventories() {
        return this.inventories;
    }

    private void createPages(int page) {
        page += 1;
        if (page > this.inventories.size()) {
            for (int i = 0; i < page; i++) {
                if (this.inventories.size() >= (i + 1))
                    continue;

                if (i == 0)
                    continue;

                this.inventories.add(API.createInventory(player, this.name + " | " + (i + 1), this.rows));
            }
        }
    }
}
