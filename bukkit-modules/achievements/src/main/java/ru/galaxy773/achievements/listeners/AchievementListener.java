package ru.galaxy773.achievements.listeners;

import ru.galaxy773.achievements.Achievements;
import ru.galaxy773.achievements.manager.AchievementManager;
import ru.galaxy773.bukkit.api.utils.listener.GListener;

public abstract class AchievementListener extends GListener<Achievements> {

    protected final AchievementManager manager;

    protected AchievementListener(Achievements plugin, AchievementManager manager) {
        super(plugin);
        this.manager = manager;
    }
}
