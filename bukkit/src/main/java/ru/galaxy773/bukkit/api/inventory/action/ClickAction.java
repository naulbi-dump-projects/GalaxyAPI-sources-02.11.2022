package ru.galaxy773.bukkit.api.inventory.action;

import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;

public interface ClickAction {

    void onClick(Player clicker, ClickType clickType, int slot);
}
