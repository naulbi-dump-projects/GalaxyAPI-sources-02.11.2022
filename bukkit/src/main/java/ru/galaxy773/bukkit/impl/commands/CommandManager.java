package ru.galaxy773.bukkit.impl.commands;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandMap;
import org.bukkit.event.EventPriority;
import org.bukkit.event.server.PluginDisableEvent;
import org.bukkit.plugin.Plugin;
import ru.galaxy773.bukkit.api.BukkitPlugin;
import ru.galaxy773.bukkit.api.commands.CommandSource;
import ru.galaxy773.bukkit.api.commands.CommandsAPI;
import ru.galaxy773.bukkit.api.utils.listener.FastEvent;
import ru.galaxy773.bukkit.api.utils.listener.FastListener;
import ru.galaxy773.bukkit.api.utils.reflection.BukkitReflectionUtil;

import java.lang.reflect.Field;
import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class CommandManager {

    private Map<String, CommandSource> commandSources = new ConcurrentHashMap<>();
    private Multimap<String, String> pluginsCommands = ArrayListMultimap.create();
    private CommandMap commandMap;

    public CommandManager(CommandsAPI commandsAPI) {
        try {
            Class<?> craftServer = BukkitReflectionUtil.getCraftBukkitClass("CraftServer");
            Field CommandSourceMap;
            CommandSourceMap = craftServer.getDeclaredField("commandMap");
            CommandSourceMap.setAccessible(true);
            this.commandMap = (CommandMap) CommandSourceMap.get(Bukkit.getServer());
            CommandSourceMap.setAccessible(false);
            FastListener.create().event(FastEvent.builder(PluginDisableEvent.class)
                    .priority(EventPriority.LOWEST)
                    .ignoreCancelled(true)
                    .handler((event -> {
                        Collection<String> commands = pluginsCommands.removeAll(event.getPlugin().getName());
                        if (commands != null) {
                            commands.forEach(commandsAPI::disableCommand);
                        }
                    }))
                    .build())
                    .register(BukkitPlugin.getInstance());

        } catch (Exception ignored) {}
    }

    public CommandSource getCommand(String commandName) {
        return commandSources.get(commandName);
    }

    public Map<String, CommandSource> getCommandSources() {
        return this.commandSources;
    }

    public void add(CommandSource commandSource, Plugin javaPlugin) {
        this.commandSources.putIfAbsent(commandSource.getName(), commandSource);
        this.pluginsCommands.put(javaPlugin.getName(), commandSource.getName());
    }

    public void remove(CommandSource commandSource) {
        this.commandSources.remove(commandSource.getName());
        this.pluginsCommands.keySet().forEach(key -> {
            pluginsCommands.get(key).remove(commandSource.getName());
        });
    }

    public CommandMap getCommandMap() {
        return this.commandMap;
    }
}
