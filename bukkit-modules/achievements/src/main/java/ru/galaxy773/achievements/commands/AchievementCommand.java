package ru.galaxy773.achievements.commands;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import ru.galaxy773.achievements.Achievements;
import ru.galaxy773.achievements.api.AchievementCategory;
import ru.galaxy773.achievements.gui.AchievementsCategoryGui;
import ru.galaxy773.achievements.gui.AchievementsGui;
import ru.galaxy773.bukkit.api.BukkitAPI;
import ru.galaxy773.bukkit.api.commands.CommandIssuer;
import ru.galaxy773.bukkit.api.commands.CommandSource;
import ru.galaxy773.bukkit.api.gui.GuiManager;
import ru.galaxy773.bukkit.impl.gui.types.AbstractGui;

public class AchievementCommand implements CommandIssuer {

    private final GuiManager<AbstractGui<?>> guiManager = BukkitAPI.getGuiManager();

    public AchievementCommand(Achievements plugin) {
        CommandSource commandSource =
                BukkitAPI.getCommandsAPI().register(plugin, "achievements",
                        this, "achievement", "achivs", "достижения");

        commandSource.setOnlyPlayers(true);
    }

    @Override
    public void execute(CommandSender sender, String commandName, String[] args) {
        if (args.length >= 1) {
            AchievementCategory category = AchievementCategory.getByName(args[0].toUpperCase());
            if (category != null) {
                AchievementsGui achievementsGui = this.guiManager.getGui(AchievementsGui.class, (Player) sender);
                achievementsGui.setCategory(category);
                achievementsGui.open();
                return;
            }
        }
        this.guiManager.getGui(AchievementsCategoryGui.class, (Player) sender).open();
    }
}
