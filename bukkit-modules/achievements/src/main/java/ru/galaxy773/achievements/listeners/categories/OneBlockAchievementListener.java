package ru.galaxy773.achievements.listeners.categories;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import ru.galaxy773.achievements.Achievements;
import ru.galaxy773.achievements.api.AchievementType;
import ru.galaxy773.achievements.listeners.AchievementListener;
import ru.galaxy773.achievements.manager.AchievementManager;
import ru.galaxy773.achievements.player.AchievementPlayer;
import ru.galaxy773.oneblock.api.event.IslandAsyncCreateEvent;

public class OneBlockAchievementListener extends AchievementListener {

    public OneBlockAchievementListener(Achievements plugin, AchievementManager manager) {
        super(plugin, manager);
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onAsyncIslandCreate(IslandAsyncCreateEvent event) {
        AchievementPlayer achievementPlayer = manager.getFromCacheOrLoad(event.getPlayer().getName());
        if (!achievementPlayer.isCompleted(AchievementType.BEGGINING))
            achievementPlayer.addProgress(AchievementType.BEGGINING, 1);
    }
}
