package ru.galaxy773.bukkit.api.hologram.lines;

import org.bukkit.inventory.ItemStack;

public interface ItemDropLine {

    ItemStack getItemStack();

    void setItem(ItemStack item);

    boolean isPickup();

    void setPickup(boolean pickup);
}
