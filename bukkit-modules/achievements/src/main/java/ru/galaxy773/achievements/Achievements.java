package ru.galaxy773.achievements;

import lombok.Getter;
import org.bukkit.plugin.java.JavaPlugin;
import ru.galaxy773.achievements.api.AchievementType;
import ru.galaxy773.achievements.commands.AchievementCommand;
import ru.galaxy773.achievements.data.AchievementData;
import ru.galaxy773.achievements.gui.AchievementsCategoryGui;
import ru.galaxy773.achievements.gui.AchievementsGui;
import ru.galaxy773.achievements.listeners.PlayerAchievementListener;
import ru.galaxy773.achievements.listeners.categories.GlobalAchievementListener;
import ru.galaxy773.achievements.listeners.categories.OneBlockAchievementListener;
import ru.galaxy773.achievements.listeners.categories.SurvivalAchievementListener;
import ru.galaxy773.achievements.manager.AchievementManager;
import ru.galaxy773.achievements.player.AchievementPlayer;
import ru.galaxy773.bukkit.api.BukkitAPI;
import ru.galaxy773.bukkit.api.game.GameType;
import ru.galaxy773.bukkit.api.gamer.BukkitGamer;
import ru.galaxy773.bukkit.api.gamer.GamerManager;
import ru.galaxy773.bukkit.api.gui.GuiManager;
import ru.galaxy773.bukkit.impl.gui.types.AbstractGui;

import java.util.Map;

public class Achievements extends JavaPlugin {

    @Getter
    private static Achievements instance;

    @Getter
    private AchievementManager manager;

    @Override
    public void onLoad() {
        instance = this;
    }

    @Override
    public void onEnable() {
        this.manager = new AchievementManager();
        this.registerListeners();
        this.registerGuis();
        new AchievementCommand(this);
    }

    @Override
    public void onDisable() {
        //ну хз, на случай перезагрузки плугманом.
        for (AchievementPlayer player : this.manager.getPlayersCache().values()) {
            Map<AchievementType, AchievementData> toSave = player.getToSave();
            if (toSave == null)
                continue;

            BukkitGamer gamer = GamerManager.getGamer(player.getName());
            if (gamer == null)
                continue;

            this.manager.saveAchievements(gamer.getPlayerID(), toSave);
        }

        //возможно то что выше при шатдаунах два раза будет
        //выполняться из-за эвента выхода, поэтому сразу очищаем
        this.manager.getPlayersCache().clear();
        this.manager.getMySql().close();
        this.unregisterGuis();
    }

    private void registerListeners() {
        new PlayerAchievementListener(this, this.manager);

        if (GameType.getCurrent() == GameType.LOBBY || GameType.getCurrent() == GameType.HUB) {
            new GlobalAchievementListener(this, this.manager);
            return;
        }

        if (GameType.getCurrent() == GameType.SURVIVAL) {
            new SurvivalAchievementListener(this, this.manager);
            return;
        }

        if (GameType.getCurrent() == GameType.ONEBLOCK) {
            new OneBlockAchievementListener(this, this.manager);
        }
    }

    private void registerGuis() {
        GuiManager<AbstractGui<?>> guiManager = BukkitAPI.getGuiManager();
        guiManager.createGui(AchievementsCategoryGui.class);
        guiManager.createGui(AchievementsGui.class);
    }

    private void unregisterGuis() {
        GuiManager<AbstractGui<?>> guiManager = BukkitAPI.getGuiManager();
        guiManager.removeGui(AchievementsCategoryGui.class);
        guiManager.removeGui(AchievementsGui.class);
    }
}
