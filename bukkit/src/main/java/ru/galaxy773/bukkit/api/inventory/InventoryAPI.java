package ru.galaxy773.bukkit.api.inventory;

import org.bukkit.entity.Player;
import ru.galaxy773.bukkit.api.inventory.action.ClickAction;
import ru.galaxy773.bukkit.api.inventory.type.GInventory;
import ru.galaxy773.bukkit.api.inventory.type.MultiInventory;
import ru.galaxy773.bukkit.api.inventory.type.ScrollInventory;

import java.util.List;

public interface InventoryAPI {

    GInventory createInventory(Player player, String name, int rows);
    GInventory createInventory(String name, int rows);
    GInventory createInventory(Player player, int rows, String key, Object... objects);
    GInventory createInventory(int rows, String key, Object... objects);

    MultiInventory createMultiInventory(Player player, String name, int rows);
    MultiInventory createMultiInventory(String name, int rows);
    MultiInventory createMultiInventory(Player player, int rows, String key, Object... objects);
    MultiInventory createMultiInventory(int rows, String key, Object... objects);


    ScrollInventory createScrollInventory(Player player, String name);
    ScrollInventory createScrollInventory(String name);

    void pageButton(List<GInventory> pages, int slotDown, int slotUp);
    void pageButton(MultiInventory inventory, int slotDown, int slotUp);

    void backButton(GInventory inventory, ClickAction action, int slot);
    void backButton(List<GInventory> pages, ClickAction action, int slot);
    void backButton(MultiInventory inventory, ClickAction action, int slot);
}
