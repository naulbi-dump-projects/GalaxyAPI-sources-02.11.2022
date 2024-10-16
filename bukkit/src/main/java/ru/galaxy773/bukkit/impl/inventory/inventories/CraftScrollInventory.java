package ru.galaxy773.bukkit.impl.inventory.inventories;

import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import ru.galaxy773.bukkit.api.BukkitAPI;
import ru.galaxy773.bukkit.api.gamer.BukkitGamer;
import ru.galaxy773.bukkit.api.inventory.GItem;
import ru.galaxy773.bukkit.api.inventory.InventoryAPI;
import ru.galaxy773.bukkit.api.inventory.type.GInventory;
import ru.galaxy773.bukkit.api.inventory.type.ScrollInventory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

public class CraftScrollInventory implements ScrollInventory {
	
    private static final InventoryAPI API = BukkitAPI.getInventoryAPI();

    private final GInventory inventory;
    private final List<GItem> scrollItem = new ArrayList<>();
    private final Map<Integer, GItem> defaultItems = new HashMap<>();

    public CraftScrollInventory(Player player, String name) {
        inventory = API.createInventory(player, name, 5);
    }

    @Override
    public void addItemScroll(GItem item) {
        scrollItem.add(item);
        setButtonScroll();
    }

    @Override
    public void addItemsScroll(List<GItem> items) {
        scrollItem.addAll(items);
        setButtonScroll();
    }

    @Override
    public void removeItemScroll(int numberItem) {
        scrollItem.remove(numberItem);
    }

    @Override
    public void removeItemsScroll() {
        scrollItem.clear();
    }

    @Override
    public void clearInventory() {
        inventory.clearInventory();
        scrollItem.clear();
        defaultItems.clear();
    }

    @Override
    public String getName() {
        return inventory.getName();
    }

    @Override
    public void setMutableSlot(int slot, boolean mutable) {
        inventory.setMutableSlot(slot, mutable);
    }

    @Override
    public boolean isMutableSlot(int slot) {
        return inventory.isMutableSlot(slot);
    }

    @Override
    public void setItem(int slot, GItem item) {
        inventory.setItem(slot, item);
        defaultItems.put(slot, item);
    }


    private void setButtonScroll() {
        //GItem up = API.createItem()
        /*
        if (i == 0 && pagesCount > 1) {
                (pages.getMessage(i)).setItem(slotUp, inventoryAPI.createItem(ItemUtil.createItemStack(
                        Material.ARROW,
                        Localization.getMessage(Lang, "PAGE_ARROW1"),
                        Localization.getList(Lang, "PAGE_ARROW_LORE", (i + 2))),
                        (achievement, clickType, slot) -> pages.getMessage(finalI + 1).openInventory(achievement)));
            } else if (i > 0 && i < pagesCount - 1) {
                (pages.getMessage(i)).setItem(slotDown, inventoryAPI.createItem(ItemUtil.createItemStack(
                        Material.ARROW,
                        Localization.getMessage(Lang, "PAGE_ARROW2"),
                        Localization.getList(Lang, "PAGE_ARROW_LORE", i)),
                        (achievement, clickType, slot) -> pages.getMessage(finalI - 1).openInventory(achievement)));
                (pages.getMessage(i)).setItem(slotUp, inventoryAPI.createItem(ItemUtil.createItemStack(
                        Material.ARROW,
                        Localization.getMessage(Lang, "PAGE_ARROW1"),
                        Localization.getList(Lang, "PAGE_ARROW_LORE", (i + 2))),
                        (achievement, clickType, slot) -> pages.getMessage(finalI + 1).openInventory(achievement)));
            } else if (pages.size() > 1 && pagesCount > 1) {
                (pages.getMessage(i)).setItem(slotDown, inventoryAPI.createItem(ItemUtil.createItemStack(
                        Material.ARROW,
                        Localization.getMessage(Lang, "PAGE_ARROW2"),
                        Localization.getList(Lang, "PAGE_ARROW_LORE", i)),
                        (achievement, clickType, slot) -> pages.getMessage(finalI - 1).openInventory(achievement)));
            }
         */
        //todo
    }

    @Override
    public void openInventory(BukkitGamer gamer) {
        this.openInventory(gamer.getPlayer());
    }

    @Override
    public void openInventory(Player player) {
        if (player == null || !player.isOnline())
            return;

        inventory.openInventory(player);
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
    public Consumer<Player> getOpenAction() {
        return this.inventory.getOpenAction();
    }

    @Override
    public Consumer<Player> getCloseAction() {
        return this.inventory.getCloseAction();
    }

    @Override
    public Inventory getHandle() {
        return this.inventory.getHandle();
    }
}
