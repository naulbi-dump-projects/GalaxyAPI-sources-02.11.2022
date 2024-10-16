package ru.galaxy773.achievements.gui;

import lombok.Setter;
import org.bukkit.entity.Player;
import ru.galaxy773.achievements.Achievements;
import ru.galaxy773.achievements.api.Achievement;
import ru.galaxy773.achievements.api.AchievementCategory;
import ru.galaxy773.achievements.api.AchievementType;
import ru.galaxy773.achievements.data.AchievementData;
import ru.galaxy773.achievements.manager.AchievementManager;
import ru.galaxy773.achievements.player.AchievementPlayer;
import ru.galaxy773.bukkit.api.inventory.GItem;
import ru.galaxy773.bukkit.api.inventory.type.MultiInventory;
import ru.galaxy773.bukkit.api.utils.bukkit.ItemBuilder;
import ru.galaxy773.bukkit.impl.gui.types.AbstractGui;
import ru.galaxy773.multiplatform.api.locale.Lang;
import ru.galaxy773.multiplatform.api.utils.StringUtil;

import java.text.SimpleDateFormat;
import java.util.Date;

public class AchievementsGui extends AbstractGui<MultiInventory> {

    private static final AchievementManager MANAGER = Achievements.getInstance().getManager();
    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd/MM/yyyy HH:mm");

    private final AchievementPlayer achievementPlayer;
    @Setter
    private AchievementCategory category;

    public AchievementsGui(Player player) {
        super(player);
        StringUtil.getCurrentDate();
        this.achievementPlayer = MANAGER.getFromCacheOrLoad(player.getName());
        this.setRecreateOnOpen(true);
    }

    @Override
    protected void createInventory() {
        this.inventory = INVENTORY_API.createMultiInventory(
                Lang.getMessage(this.category.name() + "_ACHIEVEMENTS_GUI"), 5);
    }

    @Override
    protected void setStaticItems() {
        int slot = 10;
        int page = 0;
        for (AchievementType type : AchievementType.getByCategory(this.category)) {
            if (slot == 17) {
                slot = 19;
            } else if (slot == 26) {
                slot = 28;
            } else if (slot == 35) {
                slot = 10;
                page++;
            }

            Achievement achievement = type.getAchievement();
            AchievementData data = this.achievementPlayer.getData(type);

            ItemBuilder itemBuilder = ItemBuilder.builder(achievement.getIcon())
                    .setName(achievement.getName());

            if (data != null && data.isCompleted()) {
                itemBuilder.setLore(achievement.getCompletedLore(
                        DATE_FORMAT.format(new Date(data.getCompletedDate()))));
                itemBuilder.glowing();
            } else {
                int progress = data == null ? 0 : data.getProgress();
                itemBuilder.setLore(achievement.getNotCompletedLore(
                        progress, achievement.getProgress(),
                        StringUtil.onPercentBar(progress, achievement.getProgress()),
                        StringUtil.onPercent(progress, achievement.getProgress()) + "%"));
            }

            this.inventory.setItem(page, slot, new GItem(itemBuilder.build()));

            slot++;
        }

        INVENTORY_API.backButton(this.inventory, (clicker, clickType, i) -> clicker.chat("/achievements"), 40);
        INVENTORY_API.pageButton(this.inventory, 38, 42);
    }
}
