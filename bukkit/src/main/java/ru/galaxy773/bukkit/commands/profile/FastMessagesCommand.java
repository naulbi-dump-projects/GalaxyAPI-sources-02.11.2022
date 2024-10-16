package ru.galaxy773.bukkit.commands.profile;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import ru.galaxy773.bukkit.guis.profile.donatemenu.FastMessagesGui;

public class FastMessagesCommand extends GamerCommand {

    public FastMessagesCommand() {
        super("fastmessages", "fm");
    }

    @Override
    public void execute(CommandSender sender, String commandName, String[] args) {
        openGui(FastMessagesGui.class, (Player) sender);
    }
}
