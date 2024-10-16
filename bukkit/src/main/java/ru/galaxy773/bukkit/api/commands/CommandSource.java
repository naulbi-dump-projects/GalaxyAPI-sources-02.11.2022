package ru.galaxy773.bukkit.api.commands;

import org.bukkit.command.Command;
import ru.galaxy773.multiplatform.api.gamer.constants.Group;

import java.util.List;

public interface CommandSource {

    String getName();

    Command getCommand();

    List<String> getAliases();

    Group getGroup();

    void setOnlyGroup(Group group);

    void setMinimalGroup(Group group);

    void setMinimalGroup(int level);

    void setCommandIssuer(CommandIssuer issuer);

    void setCommandTabComplete(CommandTabComplete tabComplete);

    void setOnlyConsole(boolean flag);

    void setOnlyPlayers(boolean flag);

    void setCooldown(String key, int seconds);

    void disable();
    /*String getName();

    List<String> getAliases();

    Group getGroup();

    int getMinArgs();

    int getMaxArgs();

    int getCooldown();

    boolean inOnlyPlayers();

    boolean inOnlyGroup();

    boolean isOnlyConsole();

    CommandTabComplete getTabComplete();

    void setGroup(Group group);

    void setMinArgs(int minArgs);

    void setMaxArgs(int maxArgs);

    void setCooldown(int cooldown);

    void setOnlyPlayers(boolean onlyPlayers);

    void setOnlyGroup(boolean onlyGroup);

    void setOnlyConsole(boolean onlyConsole);

    void setTabComplete(CommandTabComplete tabComplete);*/
}
