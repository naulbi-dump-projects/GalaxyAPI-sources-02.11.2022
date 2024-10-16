package ru.galaxy773.bukkit.api.commands;

import org.bukkit.command.CommandSender;

import java.util.List;

public interface CommandTabComplete {

    List<String> complete(CommandSender commandSender, String commandName, String[] args);
}
