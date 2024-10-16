package ru.galaxy773.achievements.api;

import gnu.trove.map.TIntObjectMap;
import gnu.trove.map.hash.TIntObjectHashMap;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import ru.galaxy773.bukkit.api.game.GameType;
import ru.galaxy773.bukkit.api.utils.bukkit.HeadUtil;
import ru.galaxy773.multiplatform.api.gamer.customization.tituls.TitulType;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

@AllArgsConstructor
@Getter
public enum AchievementType {

    //todo: придумать что-то нормальное с выдачей монет на режимах через ваульт
    TO_THE_MOON(1, AchievementCategory.HIDEN,
            new Achievement("TO_THE_MOON", 1, new ItemStack(Material.ELYTRA),
                    (gamer) -> {
                        gamer.setGold(gamer.getGold() + 2500, true);
                        gamer.setExp(gamer.getExp() + 2500, true);
                    })),
    BEGGINING(2, AchievementCategory.ONEBLOCK,
            new Achievement("BEGGINING", 1, GameType.ONEBLOCK.getIcon(),
                    (gamer -> {
                        gamer.setGold(gamer.getGold() + 250, true);
                        gamer.setExp(gamer.getExp() + 250, true);
                    }))),
   SOCIABLE_1(3, AchievementCategory.GLOBAL,
            new Achievement("SOCIABLE_1", 5, HeadUtil.getHead("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNTNkMTg3N2JlOTVhOWVkYjg2ZGYyMjU2ZjIzOTU4MzI0YzJlYzE5ZWY5NDI3N2NlMmZiNWMzMzAxODQxZGMifX19"),
                    (gamer -> {
                        gamer.setGold(gamer.getGold() + 250, true);
                        gamer.setExp(gamer.getExp() + 250, true);
                    }))),
    SOCIABLE_2(4, AchievementCategory.GLOBAL,
            new Achievement("SOCIABLE_2", 15, HeadUtil.getHead("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNTNkMTg3N2JlOTVhOWVkYjg2ZGYyMjU2ZjIzOTU4MzI0YzJlYzE5ZWY5NDI3N2NlMmZiNWMzMzAxODQxZGMifX19"),
                    (gamer -> {
                        gamer.setGold(gamer.getGold() + 500, true);
                        gamer.setExp(gamer.getExp() + 500, true);
                    }))),
    SOCIABLE_3(5, AchievementCategory.GLOBAL,
            new Achievement("SOCIABLE_3", 30, HeadUtil.getHead("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNTNkMTg3N2JlOTVhOWVkYjg2ZGYyMjU2ZjIzOTU4MzI0YzJlYzE5ZWY5NDI3N2NlMmZiNWMzMzAxODQxZGMifX19"),
                    (gamer -> {
                        gamer.setGold(gamer.getGold() + 1000, true);
                        gamer.setExp(gamer.getExp() + 1000, true);
                        gamer.addTitul(TitulType.SOCIABLE);
                    }))),
    DRAGON_SLAYER(6, AchievementCategory.SURVIVAL,
            new Achievement("DRAGON_SLAYER", 1, HeadUtil.getHead("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNmYzNjA2ZmY5NGRkNTI3NWE3YWE0ZTcyNDczZTZhYWZiMWZlNmIxMDQzMzhhMDUxZjA2N2ZiYmNjY2MxYTI2MyJ9fX0="),
                    (gamer -> {
                        gamer.setExp(gamer.getExp() + 2500, true);
                        gamer.setGold(gamer.getGold() + 2500, true);
                        gamer.addTitul(TitulType.DRAGON_SLAYER);
                    })));

    private final int id;
    private final AchievementCategory category;
    private final Achievement achievement;

    private static final TIntObjectMap<AchievementType> ID_TO_TYPE = new TIntObjectHashMap<>();
    private static final Map<AchievementCategory, List<AchievementType>> CATEGORY_ACHIEVEMENTS
            = new EnumMap<>(AchievementCategory.class);

    public static AchievementType getById(int id) {
        return ID_TO_TYPE.get(id);
    }

    public static List<AchievementType> getByCategory(AchievementCategory category) {
        return CATEGORY_ACHIEVEMENTS.get(category);
    }

    static {
        for (AchievementCategory category : AchievementCategory.values())
            CATEGORY_ACHIEVEMENTS.put(category, new ArrayList<>());

        for (AchievementType type : values()) {
            ID_TO_TYPE.put(type.getId(), type);
            CATEGORY_ACHIEVEMENTS.get(type.getCategory()).add(type);
        }
    }
}
