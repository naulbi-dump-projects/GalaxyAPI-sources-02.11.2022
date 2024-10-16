package ru.galaxy773.bukkit.commands.profile;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import ru.galaxy773.bukkit.guis.profile.SettingsGui;

public class SettingsCommand extends GamerCommand {

    public SettingsCommand() {
        super("settings","setting", "настройки");
    }

    @Override
    public void execute(CommandSender sender, String commandName, String[] args) {
        openGui(SettingsGui.class, (Player) sender);
    }
}
