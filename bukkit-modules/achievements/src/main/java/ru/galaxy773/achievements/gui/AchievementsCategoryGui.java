package ru.galaxy773.achievements.gui;

import org.bukkit.entity.Player;
import ru.galaxy773.achievements.Achievements;
import ru.galaxy773.achievements.api.AchievementCategory;
import ru.galaxy773.achievements.api.AchievementType;
import ru.galaxy773.achievements.manager.AchievementManager;
import ru.galaxy773.achievements.player.AchievementPlayer;
import ru.galaxy773.bukkit.api.BukkitAPI;
import ru.galaxy773.bukkit.api.gui.GuiManager;
import ru.galaxy773.bukkit.api.inventory.GItem;
import ru.galaxy773.bukkit.api.inventory.type.GInventory;
import ru.galaxy773.bukkit.api.utils.bukkit.ItemBuilder;
import ru.galaxy773.bukkit.impl.gui.types.AbstractGui;
import ru.galaxy773.multiplatform.api.locale.Lang;
import ru.galaxy773.multiplatform.api.utils.StringUtil;

import java.util.EnumMap;
import java.util.List;
import java.util.Map;

public class AchievementsCategoryGui extends AbstractGui<GInventory> {

    private static final AchievementManager MANAGER = Achievements.getInstance().getManager();
    private static final GuiManager<AbstractGui<?>> GUI_MANAGER = BukkitAPI.getGuiManager();
    private static final Map<AchievementCategory, Integer> CATEGORY_SLOTS;

    private final AchievementPlayer achievementPlayer;

    public AchievementsCategoryGui(Player player) {
        super(player);
        this.achievementPlayer = MANAGER.getFromCacheOrLoad(player.getName());
        this.createInventory();
    }

    @Override
    public void createInventory() {
        this.inventory = INVENTORY_API.createInventory(Lang.getMessage("ACHIEVEMENTS_GUI"), 6);
    }

    @Override
    public void setStaticItems() {
        INVENTORY_API.backButton(this.inventory, (clicker, clickType, slot) -> clicker.chat("/profile"), 49);

        for (AchievementCategory category : AchievementCategory.values()) {
            List<AchievementType> achievements = AchievementType.getByCategory(category);
            List<AchievementType> completedAchievements = this.achievementPlayer.getCompletedAchievements(category);
            this.inventory.setItem(CATEGORY_SLOTS.get(category), new GItem(ItemBuilder.builder(category.getItem())
                    .setName(Lang.getMessage(category.getLocaleKey() + "_NAME"))
                    .setLore(Lang.getList(category.getLocaleKey() + "_LORE",
                            completedAchievements.size(), achievements.size(),
                            StringUtil.onPercentBar(completedAchievements.size(), achievements.size()),
                            StringUtil.onPercent(completedAchievements.size(), achievements.size()) + "%"))
                    .build(),
                    ((clicker, clickType, slot) -> {
                        AchievementsGui achievementsGui = GUI_MANAGER.getGui(AchievementsGui.class, player);
                        if (achievementsGui != null) {
                            achievementsGui.setCategory(category);
                            achievementsGui.open();
                        }
                    })));
        }
    }

    static {
        CATEGORY_SLOTS = new EnumMap<>(AchievementCategory.class);
        CATEGORY_SLOTS.put(AchievementCategory.GLOBAL, 12);
        CATEGORY_SLOTS.put(AchievementCategory.HIDEN, 14);
        CATEGORY_SLOTS.put(AchievementCategory.LUCKYWARS, 20);
        CATEGORY_SLOTS.put(AchievementCategory.BEDWARS, 22);
        CATEGORY_SLOTS.put(AchievementCategory.SKYWARS, 24);
        CATEGORY_SLOTS.put(AchievementCategory.SURVIVAL, 30);
        CATEGORY_SLOTS.put(AchievementCategory.ONEBLOCK, 32);
    }
}
