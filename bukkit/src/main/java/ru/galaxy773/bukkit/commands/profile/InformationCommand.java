package ru.galaxy773.bukkit.commands.profile;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import ru.galaxy773.bukkit.guis.profile.InformationGui;

public class InformationCommand extends GamerCommand {

    public InformationCommand() {
        super("information", "info");
    }

    @Override
    public void execute(CommandSender sender, String commandName, String[] args) {
        openGui(InformationGui.class, (Player) sender);
    }
}
