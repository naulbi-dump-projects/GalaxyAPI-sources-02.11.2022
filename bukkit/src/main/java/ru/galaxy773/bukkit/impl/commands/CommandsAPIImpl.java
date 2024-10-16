package ru.galaxy773.bukkit.impl.commands;

import org.bukkit.command.Command;
import org.bukkit.command.PluginCommandYamlParser;
import org.bukkit.command.SimpleCommandMap;
import org.bukkit.plugin.Plugin;
import org.bukkit.util.StringUtil;
import ru.galaxy773.bukkit.api.commands.CommandIssuer;
import ru.galaxy773.bukkit.api.commands.CommandSource;
import ru.galaxy773.bukkit.api.commands.CommandsAPI;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

public class CommandsAPIImpl implements CommandsAPI {

    private final CommandManager commandManager = new CommandManager(this);

    @Override
    public CommandSource register(Plugin javaPlugin, String commandName, CommandIssuer commandIssuer, String[] aliases) {
        return new CraftCommand(commandManager, javaPlugin, commandName, commandIssuer, aliases);
    }

    @Override
    public CommandSource getCommand(String commandName) {
        return commandManager.getCommand(commandName);
    }

    @Override
    public Map<String, CommandSource> getCommands() {
        return commandManager.getCommandSources();
    }

    @Override
    public List<String> getCompleteString(Collection<String> seeList, String[] args) {
        String lastWord = args[args.length - 1];
        List<String> matched = new ArrayList<>();
        for(String string : seeList) {
            if (!StringUtil.startsWithIgnoreCase(string, lastWord)) {
                continue;
            }
            matched.add(string);
        }
        matched.sort(String.CASE_INSENSITIVE_ORDER);
        return matched;
    }

    @Override
    public void disableCommand(String commandName) {

        try {
            Field knownCommands = SimpleCommandMap.class.getDeclaredField("knownCommands");
            knownCommands.setAccessible(true);
            @SuppressWarnings("unchecked")
            Map<String, Command> map = (Map<String, Command>)knownCommands.get(this.commandManager.getCommandMap());
            Command command = map.remove(commandName.toLowerCase());
            if (command == null) {
                return;
            }
            command.getAliases().forEach(alias -> map.remove(alias.toLowerCase()));
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        CommandSource commandSource = commandManager.getCommand(commandName);
        if(commandSource == null) {
            return;
        }
        commandManager.remove(commandSource);
    }

    @Override
    public void disableCommand(CommandSource commandSource) {
        commandManager.remove(commandSource);
    }

    @Override
    public void disableAllCommands(Plugin javaPlugin) {
        if(javaPlugin == null) {
            return;
        }
        List<Command> commandList = PluginCommandYamlParser.parse(javaPlugin);
        commandList.forEach(command -> disableCommand(command.getName()));
    }

    /*private final CommandManager commandManager;

    public CommandsAPIImpl() {
        this.commandManager = new CommandManager(this);
    }

    @Override
    public void registerAll(CommandIssuer commandExecutor, Plugin plugin) {
        registerAll(commandExecutor, null, plugin);
    }

    @Override
    public void registerAll(CommandIssuer commandExecutor, CommandTabComplete tabComplete, Plugin plugin) {
        register(commandExecutor, null, tabComplete, plugin);
    }

    @Override
    public void register(CommandIssuer commandExecutor, String methodName, CommandTabComplete tabComplete, Plugin plugin) {
        for (Method method : commandExecutor.getClass().getDeclaredMethods()) {
            if (!method.isAnnotationPresent(Command.class)) {
                continue;
            }
            if (methodName != null && method.getName() != methodName) {
                continue;
            }
            method.setAccessible(true);
            Command command = method.getAnnotation(Command.class);
            CraftCommand craftCommand = new CraftCommand(command.name(), command.aliases(), commandExecutor, method, plugin, commandManager);
            craftCommand.setCooldown(command.cooldown());
            craftCommand.setGroup(command.group());
            craftCommand.setMaxArgs(command.maxArgs());
            craftCommand.setMinArgs(command.minArgs());
            craftCommand.setOnlyConsole(command.onlyConsole());
            craftCommand.setOnlyPlayers(command.onlyPlayers());
            craftCommand.setOnlyConsole(command.onlyConsole());
            if (tabComplete != null) {
                craftCommand.setTabComplete(tabComplete);
            }
            if (methodName != null) {
                break;
            }
        }
    }

    @Override
    public CommandSource getCommand(String commandName) {
        return commandManager.getCommand(commandName);
    }

    @Override
    public Map<String, CommandSource> getCommands() {
        return commandManager.getCommandSources();
    }

    @Override
    public void disableCommand(String commandName) {
        try {
            Field knownCommands = SimpleCommandMap.class.getDeclaredField("knownCommands");
            knownCommands.setAccessible(true);
            @SuppressWarnings("unchecked")
            Map<String, org.bukkit.command.Command> map = (Map<String, org.bukkit.command.Command>)knownCommands.get(this.commandManager.getCommandMap());
            org.bukkit.command.Command command = map.remove(commandName.toLowerCase());
            if (command == null) {
                return;
            }
            command.getAliases().forEach(alias -> map.remove(alias.toLowerCase()));
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        CommandSource commandSource = commandManager.getCommand(commandName);
        if(commandSource == null) {
            return;
        }
        commandManager.remove(commandSource);
    }

    @Override
    public void disableCommand(CommandSource commandSource) {
        commandManager.remove(commandSource);
    }

    @Override
    public void disableAllCommands(JavaPlugin javaPlugin) {
        if(javaPlugin == null) {
            return;
        }
        List<org.bukkit.command.Command> commandList = (List<org.bukkit.command.Command>)PluginCommandYamlParser.parse(javaPlugin);
        commandList.forEach(command -> disableCommand(command.getName()));
    }

    public List<String> getCompleteString(Collection<String> seeList, final String[] args) {
        String lastWord = args[args.length - 1];
        ArrayList<String> matched = new ArrayList<String>();
        for (final String string : seeList) {
            if (!StringUtil.startsWithIgnoreCase(string, lastWord)) {
                continue;
            }
            matched.add(string);
        }
        matched.sort(String.CASE_INSENSITIVE_ORDER);
        return matched;
    }*/
}
