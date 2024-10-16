package ru.galaxy773.bukkit.impl.inventory;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.event.inventory.InventoryInteractEvent;
import org.bukkit.inventory.Inventory;
import ru.galaxy773.bukkit.api.BukkitPlugin;
import ru.galaxy773.bukkit.api.inventory.GItem;
import ru.galaxy773.bukkit.api.inventory.action.ClickAction;
import ru.galaxy773.bukkit.api.inventory.action.ClickActionWithCursor;
import ru.galaxy773.bukkit.api.inventory.type.GInventory;
import ru.galaxy773.bukkit.api.utils.listener.GListener;

import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.Map;

public class GuiManagerListener extends GListener<BukkitPlugin> {

    GuiManagerListener(BukkitPlugin javaPlugin) {
        super(javaPlugin);
    }

    private final Map<String, GInventory> inventories = new HashMap<>();
    private final Map<String, GInventory> oldInventories = new HashMap<>();

    public void openInventory(Player player, GInventory inventory) {
        String name = player.getName();
        this.oldInventories.put(name, inventory);
        GInventory oldInventory = this.inventories.get(name);
        if (oldInventory != null && oldInventory.getCloseAction() != null)
            oldInventory.getCloseAction().accept(player);
        
        this.inventories.put(name, inventory);
        if (inventory.getOpenAction() != null)
            inventory.getOpenAction().accept(player);
        
        player.openInventory(inventory.getInventory());
        this.oldInventories.remove(name);
    }

    @EventHandler
    public void onClick(InventoryClickEvent e) {
        GInventory inventory = this.getInv(e);
        if (inventory == null)
            return;

        Inventory inv = e.getView().getTopInventory();
        if (!inv.getName().equalsIgnoreCase(inventory.getName()))
            return;

        int slot = e.getRawSlot();
        if (inventory.isMutableSlot(slot))
            return;

        e.setCancelled(true);
        if (slot >= 0 && slot < inv.getSize()) {
            //костыль, пусть будет
            GItem dItem = inventory.getItems().get(slot);
            if (dItem == null || inv.getItem(slot) == null) {
                return;
            }
            ClickAction clickAction = dItem.getClickAction();
            if (clickAction instanceof ClickActionWithCursor) {
                ((ClickActionWithCursor)clickAction).setCursor(e.getCursor());
            }
            clickAction.onClick((Player)e.getWhoClicked(), e.getClick(), slot);
            if (clickAction instanceof ClickActionWithCursor) {
                e.setCursor(((ClickActionWithCursor)clickAction).getCursor());
            }
        }
    }

    @EventHandler
    public void onDisableDrag(InventoryDragEvent e) {
        GInventory inventory = this.getInv(e);
        if (inventory == null)
            return;

        if (e.getInventory().getName().equalsIgnoreCase(inventory.getName())) {
            for (int slot : e.getRawSlots()) {
                if (slot < 0 || slot > inventory.size())
                    continue;

                if (inventory.isMutableSlot(slot))
                    continue;

                GItem gItem = inventory.getItems().get(slot);
                if (gItem != null)
                    e.setCancelled(true);
            }
        }
    }

    @Nullable
    private GInventory getInv(InventoryInteractEvent e) {
        if (!(e.getWhoClicked() instanceof Player)) {
            e.setCancelled(true);
            return null;
        }
        return this.inventories.get(e.getWhoClicked().getName());
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onCloseInv(InventoryCloseEvent e) {
        Player player = (Player)e.getPlayer();
        String name = player.getName();
        GInventory inventory = this.inventories.get(name);
        if (inventory == null || inventory == this.oldInventories.get(name))
            return;

        this.inventories.remove(name);
        if (inventory.getCloseAction() != null)
            inventory.getCloseAction().accept(player);
    }
}
