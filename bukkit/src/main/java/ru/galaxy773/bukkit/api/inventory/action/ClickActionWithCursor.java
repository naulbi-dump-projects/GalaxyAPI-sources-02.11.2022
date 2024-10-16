package ru.galaxy773.bukkit.api.inventory.action;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;

public abstract class ClickActionWithCursor implements ClickAction {

    @Setter
    @Getter
    private ItemStack cursor;

    @Override
    public final void onClick(Player clicker, ClickType clickType, int slot) {
        cursor = onClick(clicker, clickType, cursor, slot);
    }

    public abstract ItemStack onClick(Player clicker, ClickType clickType, ItemStack cursor, int slot);
}
