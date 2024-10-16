package ru.galaxy773.bukkit.api.commands;

import org.bukkit.plugin.Plugin;

import java.util.Collection;
import java.util.List;
import java.util.Map;

public interface CommandsAPI {

    CommandSource register(Plugin plugin, String commandName, CommandIssuer commandIssuer, String... aliases);

    CommandSource getCommand(String name);

    Map<String, CommandSource> getCommands();

    List<String> getCompleteString(Collection<String> seeList, String... args);

    void disableCommand(String commandName);
    void disableCommand(CommandSource commandSource);
    void disableAllCommands(Plugin javaPlugin);

    /*void registerAll(CommandIssuer commandExecutor, Plugin plugin);

    void registerAll(CommandIssuer commandExecutor, CommandTabComplete tabComplete, Plugin plugin);

    void register(CommandIssuer commandExecutor, String methodName, CommandTabComplete tabComplete, Plugin plugin);

    CommandSource getCommand(String commandName);

    Map<String, CommandSource> getCommands();

    void disableCommand(String commandName);
    void disableCommand(CommandSource commandSource);

    void disableAllCommands(JavaPlugin javaPlugin);

    List<String> getCompleteString(Collection<String> see, String[] args);*/
}
