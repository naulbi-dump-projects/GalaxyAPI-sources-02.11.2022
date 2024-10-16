package ru.galaxy773.achievements.api;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.bukkit.inventory.ItemStack;
import ru.galaxy773.bukkit.api.game.GameType;
import ru.galaxy773.bukkit.api.utils.bukkit.HeadUtil;

@AllArgsConstructor
@Getter
public enum AchievementCategory {

    //HIDEN выполняется в листенерах других категорий,
    //потому что в этой категории достижения с разных режимов
    HIDEN(null, "ACHIEVEMENTS_HIDEN", HeadUtil.getHead("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMmJiYzIxN2FmYzNhMTBjYjI2OGZlYzA0MjZjY2RlZTJiODM5MDZhODE2MmQ2OWY4YzZkMDY1YjVhZWJjMTE5YyJ9fX0=")),
    SURVIVAL(GameType.SURVIVAL, "ACHIEVEMENTS_SURVIVAL", GameType.SURVIVAL.getIcon()),
    ONEBLOCK(GameType.ONEBLOCK, "ACHIEVEMENTS_ONEBLOCK", GameType.ONEBLOCK.getIcon()),
    LUCKYWARS(GameType.LW, "ACHIEVEMENTS_LUCKYWARS", GameType.LW.getIcon()),
    BEDWARS(GameType.BW, "ACHIEVEMENTS_BEDWARS", GameType.BW.getIcon()),
    SKYWARS(GameType.SW, "ACHIEVEMENTS_SKYWARS", GameType.SW.getIcon()),
    GLOBAL(null, "ACHIEVEMENTS_GLOBAL", HeadUtil.getHead("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNDM4Y2YzZjhlNTRhZmMzYjNmOTFkMjBhNDlmMzI0ZGNhMTQ4NjAwN2ZlNTQ1Mzk5MDU1NTI0YzE3OTQxZjRkYyJ9fX0="));

    private final GameType gameType;
    private final String localeKey;
    private final ItemStack item;

    public static AchievementCategory getByName(String name) {
        name = name.toUpperCase();
        for (AchievementCategory category : values()) {
            if (category.name().equals(name))
                return category;
        }

        return null;
    }
}
