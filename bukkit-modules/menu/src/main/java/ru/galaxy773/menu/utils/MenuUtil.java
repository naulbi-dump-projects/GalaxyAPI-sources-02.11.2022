package ru.galaxy773.menu.utils;

import lombok.experimental.UtilityClass;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import ru.galaxy773.bukkit.api.BukkitAPI;
import ru.galaxy773.bukkit.api.core.CoreAPI;
import ru.galaxy773.bukkit.api.gamer.BukkitGamer;
import ru.galaxy773.bukkit.api.utils.bukkit.ItemUtil;
import ru.galaxy773.bukkit.api.utils.bukkit.LocationUtil;
import ru.galaxy773.multiplatform.api.placeholder.PlaceholderAPI;
import ru.galaxy773.multiplatform.api.sound.SoundType;

import java.util.List;
import java.util.stream.Collectors;

@UtilityClass
public final class MenuUtil {

    private final PlaceholderAPI PLACEHOLDER_API = BukkitAPI.getPlaceholderAPI();
    private final CoreAPI CORE_API = BukkitAPI.getCoreAPI();

    public boolean containsPlaceholders(String input) {
        return PLACEHOLDER_API.getPlaceholders().values().stream()
                .anyMatch(list -> list.stream().anyMatch(placeholder -> input.contains(placeholder.getName())));
    }

    public boolean containsPlaceholders(List<String> inputs) {
        return inputs.stream().anyMatch(MenuUtil::containsPlaceholders);
    }

    public String applyPlaceholders(BukkitGamer gamer, String input) {
        return PLACEHOLDER_API.applyAll(gamer, input);
    }

    public List<String> applyPlaceholders(BukkitGamer gamer, List<String> inputs) {
        return inputs.stream().map(input -> MenuUtil.applyPlaceholders(gamer, input)).collect(Collectors.toList());
    }

    public void applyCommands(BukkitGamer gamer, List<String> commands) {
        Player player = gamer.getPlayer();
        commands.forEach(command -> {
            String argument = null;
            if (command.contains(":")) {
                argument = command.substring(command.split(":")[0].length() + 1, command.length());
            }
            if (command.startsWith("server:")) {
                CORE_API.sendToServer(gamer, argument);
            } else if (command.startsWith("teleport:")) {
                player.teleport(LocationUtil.stringToLocation(argument, (argument.split(";").length > 4 ? 1 : 0) != 0));
            } else if (command.startsWith("tell:")) {
                player.sendMessage(PLACEHOLDER_API.applyGamer(gamer, argument));
            } else if (command.startsWith("exp:")) {
                gamer.addExp(Integer.parseInt(argument));
            } else if (command.startsWith("sound:")) {
                gamer.playSound(SoundType.valueOf(argument));
            } else if (command.startsWith("title:")) {
                argument = PLACEHOLDER_API.applyGamer(gamer, argument);
                gamer.sendTitle(argument.split(";")[0], argument.split(";")[1], 20, 40, 20);
            } else if (command.startsWith("actionbar:")) {
                gamer.sendActionBar(PLACEHOLDER_API.applyGamer(gamer, argument));
            } else if (command.startsWith("console:")) {
                Bukkit.dispatchCommand(Bukkit.getConsoleSender(), PLACEHOLDER_API.applyGamer(gamer, argument));
            } else if (command.startsWith("give:")) {
                ItemStack item = ItemUtil.stringToItem(argument);
                if (player.getInventory().firstEmpty() == -1) {
                    player.getWorld().dropItem(player.getLocation(), item);
                } else {
                    player.getInventory().addItem(item);
                }
            } else if (command.equals("close")) {
                player.closeInventory();
            } else {
                player.chat(command);
            }
        });
    }
}

