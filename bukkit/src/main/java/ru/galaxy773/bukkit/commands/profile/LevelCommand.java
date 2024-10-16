package ru.galaxy773.bukkit.commands.profile;

import org.bukkit.command.CommandSender;
import ru.galaxy773.bukkit.api.gamer.BukkitGamer;
import ru.galaxy773.bukkit.api.gamer.GamerManager;
import ru.galaxy773.multiplatform.api.locale.Lang;

public class LevelCommand extends GamerCommand {

    public LevelCommand() {
        super("level", "lvl", "уровень");
    }

    @Override
    public void execute(CommandSender sender, String commandName, String[] args) {
        BukkitGamer gamer = GamerManager.getGamer(sender.getName());
        gamer.sendMessages(Lang.getList("COMMAND_LEVEL", gamer.getLevel(), gamer.getExpNextLevel() - gamer.getExp()));
    }
}
