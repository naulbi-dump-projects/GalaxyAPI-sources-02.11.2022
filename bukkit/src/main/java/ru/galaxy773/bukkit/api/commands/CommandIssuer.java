package ru.galaxy773.bukkit.api.commands;

import org.bukkit.command.CommandSender;

public interface CommandIssuer {

    //annotation - remove
    void execute(CommandSender sender, String commandName, String[] args);
}
