package ru.galaxy773.cosmetics.commands;

import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import ru.galaxy773.bukkit.api.BukkitAPI;
import ru.galaxy773.bukkit.api.commands.CommandIssuer;
import ru.galaxy773.bukkit.api.commands.CommandSource;
import ru.galaxy773.bukkit.api.gui.GuiManager;
import ru.galaxy773.bukkit.impl.gui.types.AbstractGui;
import ru.galaxy773.cosmetics.Cosmetics;

public abstract class CosmeticsCommand implements CommandIssuer {

    private static final GuiManager<AbstractGui<?>> profileGuiManager = BukkitAPI.getGuiManager();

    public CosmeticsCommand(String name, String ... aliases) {
        CommandSource commandSource = BukkitAPI.getCommandsAPI().register((Plugin)Cosmetics.getInstance(), name, this, aliases);
        commandSource.setOnlyPlayers(true);
    }

    public void openGui(Class<? extends AbstractGui<?>> guiClass, Player player) {
        profileGuiManager.getGui(guiClass, player).open();
    }
}

