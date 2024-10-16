package ru.galaxy773.bukkit.api.entity;

import org.bukkit.inventory.ItemStack;

import java.util.Map;

public interface EntityEquip {

    ItemStack getHelmet();
    ItemStack getChestplate();
    ItemStack getLeggings();
    ItemStack getBoots();
    ItemStack getItemInMainHand();
    ItemStack getItemInOffHand();
    ItemStack getItem(EquipType equipType);

    void setItem(EquipType equipType, ItemStack itemStack);

    void setHelmet(ItemStack helmet);
    void setChestplate(ItemStack chestplate);
    void setLeggings(ItemStack leggings);
    void setBoots(ItemStack boots);
    void setItemInMainHand(ItemStack item);
    void setItemInOffHand(ItemStack item);

    boolean removeItem(EquipType equipType);
    void removeItems();

    Map<EquipType, ItemStack> getItemsEquip();
}
