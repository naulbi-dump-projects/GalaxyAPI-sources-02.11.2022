package ru.galaxy773.bukkit.commands;

import org.bukkit.command.CommandSender;
import ru.galaxy773.bukkit.api.BukkitAPI;
import ru.galaxy773.bukkit.api.BukkitPlugin;
import ru.galaxy773.bukkit.api.commands.CommandIssuer;
import ru.galaxy773.bukkit.api.commands.CommandSource;
import ru.galaxy773.bukkit.api.commands.CommandsAPI;
import ru.galaxy773.multiplatform.api.gamer.constants.Group;
import ru.galaxy773.multiplatform.api.locale.Lang;

public class ApiCommands implements CommandIssuer {

    public ApiCommands(BukkitPlugin plugin) {
        CommandsAPI commandsAPI = BukkitAPI.getCommandsAPI();
        CommandSource commandSource = commandsAPI.register(plugin, "BukkitAPI", this, "gapi");
        commandSource.setOnlyGroup(Group.ADMIN);
    }
    /*public ApiCommands() {
        BukkitAPI.getCommandsAPI().registerAll(this, (commandSender, commandName, args) -> {
            if (args.length == 0) {
                return Arrays.asList("reload");
            }
            if (!args[0].equalsIgnoreCase("reload")) {
                return Collections.emptyList();
            }
            return Arrays.stream(Bukkit.getPluginManager().getPlugins()).map(Plugin::getName).collect(Collectors.toList());
        }, BukkitPlugin.getInstance());
    }*/

    @Override
    public void execute(CommandSender sender, String commandName, String[] args) {
        if (args.length < 1 || !args[0].equalsIgnoreCase("reload")) {
            sender.sendMessage(Lang.getMessage("COMMAND_RELOAD_LANG_USE"));
            return;
        }
        Lang.load();
        sender.sendMessage(Lang.getMessage("LANG_RELOAD"));
    }
}
