package ru.galaxy773.bukkit.api.usableitem;

import org.bukkit.block.Block;
import org.bukkit.entity.Player;

public interface ClickAction {

    /**
     * Метод, который будет вызван при клике по UsableItem
     * @param player игрок
     * @param clickType тип клика
     */
    void onClick(Player player, ClickType clickType, Block clickedBlock);
}
