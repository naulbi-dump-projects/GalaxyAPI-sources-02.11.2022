package ru.galaxy773.bukkit.commands.profile;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import ru.galaxy773.bukkit.guis.profile.donatemenu.QuitMessagesGui;

public class QuitMessagesCommand extends GamerCommand {

    public QuitMessagesCommand() {
        super("quitmessages", "quitmessage", "qm");
    }

    @Override
    public void execute(CommandSender sender, String commandName, String[] args) {
        openGui(QuitMessagesGui.class, (Player) sender);
    }
}
