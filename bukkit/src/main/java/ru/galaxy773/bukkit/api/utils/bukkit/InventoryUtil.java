package ru.galaxy773.bukkit.api.utils.bukkit;

import lombok.experimental.UtilityClass;

@UtilityClass
public class InventoryUtil {

    public int getSlotByXY(int x, int y) {
        return 9 * y + x - 10;
    }

    public int getPagesCount(int elem, int elemPage) {
        return (elem + elemPage - 1) / elemPage;
    }
}
