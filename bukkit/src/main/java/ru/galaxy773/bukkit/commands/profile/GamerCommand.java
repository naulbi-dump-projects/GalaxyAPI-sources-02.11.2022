package ru.galaxy773.bukkit.commands.profile;

import org.bukkit.entity.Player;
import ru.galaxy773.bukkit.api.BukkitAPI;
import ru.galaxy773.bukkit.api.BukkitPlugin;
import ru.galaxy773.bukkit.api.commands.CommandIssuer;
import ru.galaxy773.bukkit.api.commands.CommandSource;
import ru.galaxy773.bukkit.api.commands.CommandsAPI;
import ru.galaxy773.bukkit.api.gui.GuiManager;
import ru.galaxy773.bukkit.impl.gui.types.AbstractGui;

public abstract class GamerCommand implements CommandIssuer {

    private static final BukkitPlugin PLUGIN = BukkitPlugin.getInstance();
    private static final CommandsAPI COMMANDS_API = BukkitAPI.getCommandsAPI();
    private static final GuiManager<AbstractGui<?>> GUI_MANAGER = BukkitAPI.getGuiManager();

    public GamerCommand(String command, String... aliases) {
        CommandSource commandSource = COMMANDS_API.register(PLUGIN, command, this, aliases);
        commandSource.setOnlyPlayers(true);
    }

    public void openGui(Class<? extends AbstractGui<?>> guiClass, Player player) {
        GUI_MANAGER.getGui(guiClass, player).open();
    }
}
