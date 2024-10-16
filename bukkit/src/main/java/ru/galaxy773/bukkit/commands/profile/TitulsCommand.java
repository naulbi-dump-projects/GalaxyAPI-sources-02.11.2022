package ru.galaxy773.bukkit.commands.profile;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import ru.galaxy773.bukkit.guis.profile.tituls.TitulsCategoryGui;

public class TitulsCommand extends GamerCommand {

    public TitulsCommand() {
        super("tituls", "titul", "титул", "титулы");
    }

    @Override
    public void execute(CommandSender sender, String commandName, String[] args) {
        openGui(TitulsCategoryGui.class, (Player) sender);
    }
}
