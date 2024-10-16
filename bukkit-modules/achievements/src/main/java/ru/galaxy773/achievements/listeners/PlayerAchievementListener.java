package ru.galaxy773.achievements.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import ru.galaxy773.achievements.Achievements;
import ru.galaxy773.achievements.api.AchievementType;
import ru.galaxy773.achievements.api.event.GamerAchievementCompleteEvent;
import ru.galaxy773.achievements.data.AchievementData;
import ru.galaxy773.achievements.manager.AchievementManager;
import ru.galaxy773.achievements.player.AchievementPlayer;
import ru.galaxy773.bukkit.api.event.gamer.async.AsyncGamerQuitEvent;
import ru.galaxy773.bukkit.api.gamer.BukkitGamer;
import ru.galaxy773.multiplatform.api.utils.StringUtil;

import java.util.Map;

public class PlayerAchievementListener extends AchievementListener {

    public PlayerAchievementListener(Achievements plugin, AchievementManager achievementManager) {
        super(plugin, achievementManager);
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onAsyncQuit(AsyncGamerQuitEvent event) {
        AchievementPlayer player = this.manager.getFromCache(event.getGamer().getName());
        if (player == null)
            return;

        Map<AchievementType, AchievementData> toSave = player.getToSave();
        if (toSave == null)
            return;

        this.manager.saveAchievements(event.getGamer().getPlayerID(), toSave);
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onComplete(GamerAchievementCompleteEvent event) {
        BukkitGamer gamer = event.getGamer();
        gamer.sendMessage("");
        gamer.sendMessage("§8§m====================================================");
        gamer.sendMessage("");
        gamer.sendMessage("            §a§k|||§6§l ДОСТИЖЕНИЕ ВЫПОЛНЕНО §a§k|||");
        gamer.sendMessage(StringUtil.stringToCenter(
                event.getAchievementType().getAchievement().getName(), 50));
        gamer.sendMessage("");
        gamer.sendMessage("§8§m====================================================");
        gamer.sendMessage("");
    }
}
