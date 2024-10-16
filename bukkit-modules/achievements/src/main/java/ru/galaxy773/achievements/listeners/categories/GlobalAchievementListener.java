package ru.galaxy773.achievements.listeners.categories;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.player.PlayerMoveEvent;
import ru.galaxy773.achievements.Achievements;
import ru.galaxy773.achievements.api.AchievementType;
import ru.galaxy773.achievements.listeners.AchievementListener;
import ru.galaxy773.achievements.manager.AchievementManager;
import ru.galaxy773.achievements.player.AchievementPlayer;
import ru.galaxy773.bukkit.api.event.gamer.GamerFriendEvent;
import ru.galaxy773.bukkit.api.gamer.BukkitGamer;
import ru.galaxy773.multiplatform.impl.gamer.sections.FriendsSection;

public class GlobalAchievementListener extends AchievementListener {

    public GlobalAchievementListener(Achievements plugin, AchievementManager manager) {
        super(plugin, manager);
    }

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onMove(PlayerMoveEvent event) {
        Player player = event.getPlayer();
        if (player.getLocation().getBlockY() < 1000)
            return;

        AchievementPlayer achievementPlayer = this.manager.getFromCacheOrLoad(player.getName());
        if (!achievementPlayer.isCompleted(AchievementType.TO_THE_MOON))
            achievementPlayer.addProgress(AchievementType.TO_THE_MOON, 1);
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onFriendAdd(GamerFriendEvent event) {
        AchievementPlayer player = this.manager.getFromCacheOrLoad(event.getGamer().getName());
        updateProgress(event.getGamer(), player, AchievementType.SOCIABLE_1);
        updateProgress(event.getGamer(), player, AchievementType.SOCIABLE_2);
        updateProgress(event.getGamer(), player, AchievementType.SOCIABLE_3);
    }

    private void updateProgress(BukkitGamer gamer, AchievementPlayer player, AchievementType type) {
        player.setProgress(type, gamer.getSection(FriendsSection.class).getFriends().size());
    }
}
