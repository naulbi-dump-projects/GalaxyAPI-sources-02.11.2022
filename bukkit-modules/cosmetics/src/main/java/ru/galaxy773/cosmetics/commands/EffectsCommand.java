package ru.galaxy773.cosmetics.commands;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import ru.galaxy773.cosmetics.guis.EffectsMainGui;

public class EffectsCommand extends CosmeticsCommand {

    public EffectsCommand() {
        super("effects", "effect", "\u044d\u0444\u0444\u0435\u043a\u0442\u044b");
    }

    public void execute(CommandSender commandSender, String s, String[] strings) {
        this.openGui(EffectsMainGui.class, (Player)commandSender);
    }
}

