package ru.galaxy773.bukkit.commands.profile;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import ru.galaxy773.bukkit.guis.profile.donatemenu.JoinMessagesGui;

public class JoinMessagesCommand extends GamerCommand {

    public JoinMessagesCommand() {
        super("joinmessages", "joinmessage", "jm");
    }

    @Override
    public void execute(CommandSender sender, String commandName, String[] args) {
        openGui(JoinMessagesGui.class, (Player) sender);
    }
}
