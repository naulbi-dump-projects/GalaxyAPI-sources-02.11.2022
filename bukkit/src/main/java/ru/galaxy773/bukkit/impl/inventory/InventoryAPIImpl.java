package ru.galaxy773.bukkit.impl.inventory;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import ru.galaxy773.bukkit.api.BukkitPlugin;
import ru.galaxy773.bukkit.api.inventory.GItem;
import ru.galaxy773.bukkit.api.inventory.InventoryAPI;
import ru.galaxy773.bukkit.api.inventory.action.ClickAction;
import ru.galaxy773.bukkit.api.inventory.type.GInventory;
import ru.galaxy773.bukkit.api.inventory.type.MultiInventory;
import ru.galaxy773.bukkit.api.inventory.type.ScrollInventory;
import ru.galaxy773.bukkit.api.utils.bukkit.HeadUtil;
import ru.galaxy773.bukkit.api.utils.bukkit.ItemBuilder;
import ru.galaxy773.bukkit.impl.inventory.inventories.CraftGInventory;
import ru.galaxy773.bukkit.impl.inventory.inventories.CraftMultiInventory;
import ru.galaxy773.bukkit.impl.inventory.inventories.CraftScrollInventory;
import ru.galaxy773.multiplatform.api.locale.Lang;

import java.util.List;

public class InventoryAPIImpl implements InventoryAPI {

    private final GuiManagerListener guiManagerListener;

    public InventoryAPIImpl(BukkitPlugin javaPlugin) {
        this.guiManagerListener = new GuiManagerListener(javaPlugin);
    }

    @Override
    public GInventory createInventory(Player player, String name, int rows) {
        return new CraftGInventory(name, rows, guiManagerListener);
    }

    @Override
    public GInventory createInventory(String name, int rows) {
        return createInventory(null, name, rows);
    }

    @Override
    public GInventory createInventory(Player player, int rows, String key, Object... objects) {
        return createInventory(player, Lang.getMessage(key, objects), rows);
    }

    @Override
    public GInventory createInventory(int rows, String key, Object... objects) {
        return createInventory(Lang.getMessage(key, objects), rows);
    }

    @Override
    public MultiInventory createMultiInventory(Player player, String name, int rows) {
        return new CraftMultiInventory(player, name, rows);
    }

    @Override
    public MultiInventory createMultiInventory(String name, int rows) {
        return createMultiInventory(null, name, rows);
    }

    @Override
    public MultiInventory createMultiInventory(Player player, int rows, String key, Object... objects) {
        return createMultiInventory(player, Lang.getMessage(key, objects), rows);
    }

    @Override
    public MultiInventory createMultiInventory(int rows, String key, Object... objects) {
        return createMultiInventory(Lang.getMessage(key, objects), rows);
    }

    @Override
    public ScrollInventory createScrollInventory(Player player, String name) {
        return new CraftScrollInventory(player, name);
    }

    @Override
    public ScrollInventory createScrollInventory(String name) {
        return createScrollInventory(null, name);
    }

    @Override
    public void pageButton(List<GInventory> pages, int slotDown, int slotUp) {
        for (int i = 0; i < pages.size(); i++) {
            int finalI = i;

            (pages.get(i)).setItem(slotDown, new GItem(ItemBuilder.builder(HeadUtil.getForwardHead())
                    .setName(Lang.getMessage( "PAGE_BACK_NAME"))
                    .setLore(Lang.getList( "PAGE_BACK_LORE", i))
                    .build(),
                    (player, clickType, slot) -> {
                        if (finalI == 0)
                            return;

                        pages.get(finalI - 1).openInventory(player);
                    }));

            (pages.get(i)).setItem(slotUp, new GItem(ItemBuilder.builder(HeadUtil.getForwardHead())
                    .setName(Lang.getMessage( "PAGE_NEXT_NAME"))
                    .setLore(Lang.getList( "PAGE_NEXT_LORE", (i + 2)))
                    .build(),
                    (player, clickType, slot) -> {
                        if (finalI == pages.size() - 1)
                            return;

                        pages.get(finalI + 1).openInventory(player);
                    }));
            /*if (i == 0 && pagesCount > 1 && pages.size() > 1) {
                (pages.get(i)).setItem(slotUp, new GItem(ItemBuilder.builder(HeadUtil.getForwardHead())
                                .setName(Lang.getMessage( "PAGE_NEXT_NAME"))
                                .setLore(Lang.getList( "PAGE_NEXT_LORE", (i + 2)))
                                .build(),
                        (player, clickType, slot) -> pages.get(finalI + 1).openInventory(player)));
            } else if (i > 0 && i < pagesCount - 1 && pages.size() > i + 1) {
                (pages.get(i)).setItem(slotDown, new GItem(ItemBuilder.builder(HeadUtil.getForwardHead())
                                .setName(Lang.getMessage( "PAGE_BACK_NAME"))
                                .setLore(Lang.getList( "PAGE_BACK_LORE", i))
                                .build(),
                        (player, clickType, slot) -> pages.get(finalI - 1).openInventory(player)));
                (pages.get(i)).setItem(slotUp, new GItem(ItemBuilder.builder(HeadUtil.getForwardHead())
                                .setName(Lang.getMessage( "PAGE_NEXT_NAME"))
                                .setLore(Lang.getList( "PAGE_NEXT_LORE", (i + 2)))
                                .build(),
                        (player, clickType, slot) -> pages.get(finalI + 1).openInventory(player)));
            } else if (pages.size() > 1 && pagesCount > 1) {
                (pages.get(i)).setItem(slotDown, new GItem(ItemBuilder.builder(HeadUtil.getForwardHead())
                                .setName(Lang.getMessage( "PAGE_BACK_NAME"))
                                .setLore(Lang.getList( "PAGE_BACK_LORE", i))
                                .build(),
                        (player, clickType, slot) -> pages.get(finalI - 1).openInventory(player)));
            }*/
        }
    }

    @Override
    public void pageButton(MultiInventory inventory, int slotDown, int slotUp) {
        pageButton(inventory.getInventories(), slotDown, slotUp);
    }

    @Override
    public void backButton(GInventory inventory, ClickAction action, int slot) {
        inventory.setItem(slot, new GItem(ItemBuilder.builder(Material.SPECTRAL_ARROW)
                .setName(Lang.getMessage("PROFILE_BACK_ITEM_NAME"))
                .setLore(Lang.getList("PROFILE_BACK_ITEM_LORE"))
                .build(), action));
    }

    @Override
    public void backButton(List<GInventory> pages, ClickAction action, int slot) {
        pages.forEach(inventory -> backButton(inventory, action, slot));
    }

    @Override
    public void backButton(MultiInventory inventory, ClickAction action, int slot) {
        backButton(inventory.getInventories(), action, slot);
    }
}
