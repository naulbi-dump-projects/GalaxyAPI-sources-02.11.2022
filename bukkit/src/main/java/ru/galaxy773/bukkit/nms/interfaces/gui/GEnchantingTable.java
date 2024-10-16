package ru.galaxy773.bukkit.nms.interfaces.gui;

import ru.galaxy773.bukkit.nms.types.EnchantingSlot;
import org.bukkit.inventory.ItemStack;

public interface GEnchantingTable extends GNmsGui {

    void addItem(EnchantingSlot slot, ItemStack stack);

    void setTitle(String title);
}
