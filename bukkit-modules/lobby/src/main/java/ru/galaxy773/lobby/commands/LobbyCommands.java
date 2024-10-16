package ru.galaxy773.lobby.commands;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import ru.galaxy773.bukkit.api.BukkitAPI;
import ru.galaxy773.bukkit.api.commands.CommandIssuer;
import ru.galaxy773.bukkit.api.commands.CommandSource;
import ru.galaxy773.lobby.Lobby;
import ru.galaxy773.lobby.config.GameConfig;

public class LobbyCommands implements CommandIssuer {

    private final GameConfig gameConfig;

    public LobbyCommands(Lobby javaPlugin, GameConfig gameConfig) {
        this.gameConfig = gameConfig;
        CommandSource commandSource = BukkitAPI.getCommandsAPI().register(javaPlugin, "spawn", this, "\u0421\u0403\u0420\u0457\u0420\u00b0\u0420\u0406\u0420\u0405");
        commandSource.setOnlyPlayers(true);
    }

    public void execute(CommandSender commandSender, String s, String[] strings) {
        Player player = (Player)commandSender;
        player.teleport(this.gameConfig.getSpawn());
    }
}

