package ru.galaxy773.bukkit.api.hologram.lines;

import org.bukkit.inventory.ItemStack;

public interface ItemFloatingLine {

    void setRotate(boolean rotate);

    boolean isRotate();

    ItemStack getItem();

    void setItem(ItemStack item);
}
