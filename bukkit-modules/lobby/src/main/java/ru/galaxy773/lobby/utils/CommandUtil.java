package ru.galaxy773.lobby.utils;

import lombok.experimental.UtilityClass;
import org.bukkit.entity.Player;
import ru.galaxy773.bukkit.api.BukkitAPI;
import ru.galaxy773.bukkit.api.core.CoreAPI;

@UtilityClass
public final class CommandUtil {

    private final CoreAPI CORE_API = BukkitAPI.getCoreAPI();

    public void runCommand(Player player, String command) {
        if (command.startsWith("server:")) {
            CORE_API.sendToServer(player, command.split(":")[1]);
            return;
        }
        player.chat(command);
    }
}

