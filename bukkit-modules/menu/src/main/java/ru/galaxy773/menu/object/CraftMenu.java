/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  gnu.trove.map.TIntObjectMap
 *  gnu.trove.map.hash.TIntObjectHashMap
 *  org.bukkit.entity.Player
 *  ru.galaxy773.api.GalaxyAPI
 *  ru.galaxy773.api.inventory.GItem
 *  ru.galaxy773.api.inventory.InventoryAPI
 *  ru.galaxy773.api.inventory.type.GInventory
 *  ru.galaxy773.base.gamer.Gamer
 */
package ru.galaxy773.menu.object;

import gnu.trove.map.TIntObjectMap;
import gnu.trove.map.hash.TIntObjectHashMap;
import org.bukkit.entity.Player;
import ru.galaxy773.bukkit.api.BukkitAPI;
import ru.galaxy773.bukkit.api.gamer.BukkitGamer;
import ru.galaxy773.bukkit.api.inventory.GItem;
import ru.galaxy773.bukkit.api.inventory.InventoryAPI;
import ru.galaxy773.bukkit.api.inventory.type.GInventory;
import ru.galaxy773.menu.api.menu.Menu;
import ru.galaxy773.menu.api.menu.MenuItem;

import java.util.Collection;
import java.util.function.Consumer;

public class CraftMenu implements Menu {

    private static final InventoryAPI INVENTORY_API = BukkitAPI.getInventoryAPI();
    private final String title;
    private final int rows;
    private final TIntObjectMap<MenuItem> items = new TIntObjectHashMap<>();
    private final GInventory inventory;

    public CraftMenu(String title, int rows, Collection<MenuItem> items) {
        this.title = title;
        this.rows = rows;
        this.inventory = INVENTORY_API.createInventory(this.title, this.rows);
        items.forEach(this::addItem);
    }

    @Override
    public MenuItem getItem(int slot) {
        return this.items.get(slot);
    }

    @Override
    public Collection<MenuItem> getItems() {
        return this.items.valueCollection();
    }

    @Override
    public void addItem(MenuItem item) {
        this.items.put(item.getSlot(), item);
        if (!item.isDynamic()) {
            this.inventory.setItem(item.getSlot(), item.getClickAction() == null ? new GItem(item.getItemStack()) : new GItem(item.getItemStack(), item.getClickAction()));
        }
    }

    @Override
    public void addItems(Collection<MenuItem> items) {
        items.forEach(this::addItem);
    }

    @Override
    public void removeItem(int slot) {
        this.items.remove(slot);
        this.inventory.removeItem(slot);
    }

    @Override
    public void setOpenAction(Consumer<Player> openAction) {
        this.inventory.setOpenAction(openAction);
    }

    @Override
    public void setCloseAction(Consumer<Player> closeAction) {
        this.inventory.setCloseAction(closeAction);
    }

    @Override
    public void open(BukkitGamer gamer) {
        this.items.valueCollection().parallelStream().filter(MenuItem::isDynamic).forEach(item -> this.inventory.setItem(item.getSlot(), item.getClickAction() == null ? new GItem(item.getDynamicItemStack(gamer)) : new GItem(item.getDynamicItemStack(gamer), item.getClickAction())));
        this.inventory.openInventory(gamer);
    }

    @Override
    public String getTitle() {
        return this.title;
    }

    @Override
    public int getRows() {
        return this.rows;
    }

    @Override
    public GInventory getInventory() {
        return this.inventory;
    }
}

