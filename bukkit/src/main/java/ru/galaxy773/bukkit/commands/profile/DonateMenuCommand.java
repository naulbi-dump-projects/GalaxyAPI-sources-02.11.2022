package ru.galaxy773.bukkit.commands.profile;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import ru.galaxy773.bukkit.guis.profile.donatemenu.DonateMenuGui;

public class DonateMenuCommand extends GamerCommand {

    public DonateMenuCommand() {
        super("donatemenu", "dm", "донатменю", "customization", "кастомизация");
    }

    @Override
    public void execute(CommandSender sender, String commandName, String[] args) {
        openGui(DonateMenuGui.class, (Player) sender);
    }
}
