package ru.galaxy773.menu.api.menu;

import org.bukkit.inventory.ItemStack;
import ru.galaxy773.bukkit.api.gamer.BukkitGamer;
import ru.galaxy773.bukkit.api.inventory.action.ClickAction;
import ru.galaxy773.bukkit.api.utils.bukkit.InventoryUtil;

public interface MenuItem {

    ItemStack getItemStack();

    ItemStack getDynamicItemStack(BukkitGamer gamer);

    ClickAction getClickAction();

    boolean isDynamic();

    int getSlot();

    void setClickAction(ClickAction action);

    void setSlot(int var1);

    default void setSlot(int x, int y) {
        this.setSlot(InventoryUtil.getSlotByXY(x, y));
    }
}

