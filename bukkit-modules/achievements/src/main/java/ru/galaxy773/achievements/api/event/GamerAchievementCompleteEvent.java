package ru.galaxy773.achievements.api.event;

import lombok.Getter;
import ru.galaxy773.achievements.api.AchievementType;
import ru.galaxy773.bukkit.api.event.gamer.GamerEvent;
import ru.galaxy773.bukkit.api.gamer.BukkitGamer;

@Getter
public class GamerAchievementCompleteEvent extends GamerEvent {

    private final AchievementType achievementType;

    public GamerAchievementCompleteEvent(BukkitGamer gamer, AchievementType achievementType) {
        super(gamer);
        this.achievementType = achievementType;
    }
}
