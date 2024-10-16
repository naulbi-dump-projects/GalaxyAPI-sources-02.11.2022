package ru.galaxy773.bukkit.impl.commands;

import com.google.common.collect.ImmutableList;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.SimpleCommandMap;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import ru.galaxy773.bukkit.api.BukkitAPI;
import ru.galaxy773.bukkit.api.commands.CommandIssuer;
import ru.galaxy773.bukkit.api.commands.CommandSource;
import ru.galaxy773.bukkit.api.commands.CommandTabComplete;
import ru.galaxy773.bukkit.api.gamer.BukkitGamer;
import ru.galaxy773.bukkit.api.gamer.GamerManager;
import ru.galaxy773.multiplatform.api.gamer.constants.Group;
import ru.galaxy773.multiplatform.api.locale.Lang;
import ru.galaxy773.multiplatform.api.utils.cooldown.Cooldown;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Getter
@Setter
public class CraftCommand extends Command implements CommandSource {

    private CommandManager commandManager;
    private CommandIssuer commandIssuer;
    private CommandTabComplete commandTabComplete;
    private Group group;
    private boolean onlyConsole;
    private boolean onlyPlayers;
    private boolean onlyGroup;
    private boolean minimalGroup;
    private int cooldown;
    private String cooldownType;

    CraftCommand(CommandManager commandManager, Plugin javaPlugin, String command, CommandIssuer commandIssuer, String... aliases) {
        super(command, "", "", Arrays.asList(aliases));
        this.commandManager = commandManager;
        this.commandIssuer = commandIssuer;
        this.group = Group.DEFAULT;
        this.cooldown = 5;
        this.cooldownType = "command_cooldown";
        this.registerCommand();
        commandManager.add(this, javaPlugin);
    }

    @SuppressWarnings("unchecked")
    private void registerCommand() {
        List<String> commands = new ArrayList<>(this.getAliases());
        commands.add(this.getName());
        try {
            Method register = SimpleCommandMap.class.getDeclaredMethod("register", String.class, Command.class, Boolean.TYPE, String.class);
            register.setAccessible(true);
            commands.forEach(command -> {
                try {
                    register.invoke(this.commandManager.getCommandMap(), command, this, !this.getName().equals(command), "BukkitAPI");
                } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
                    e.printStackTrace();
                }
            });
            register.setAccessible(false);
            Field knownCommands = SimpleCommandMap.class.getDeclaredField("knownCommands");
            knownCommands.setAccessible(true);
            Map<String, Command> map = (Map<String, Command>)knownCommands.get(this.commandManager.getCommandMap());
            commands.forEach(command ->  map.put(command.toLowerCase(), this));
            knownCommands.setAccessible(false);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public List<String> tabComplete(CommandSender commandSender, String alias, String[] args) throws IllegalArgumentException {
        if(this.onlyPlayers && !(commandSender instanceof Player)) {
            return ImmutableList.of();
        }
        if(commandSender == null) {
            return ImmutableList.of();
        }
        if(commandSender instanceof Player) {
            BukkitGamer gamer = GamerManager.getGamer(commandSender.getName());
            if(this.onlyGroup && gamer.getGroup() != this.group || this.minimalGroup && gamer.getGroup().getLevel() < this.group.getLevel()) {
                return ImmutableList.of();
            }
        }
        if(this.commandTabComplete == null) {
            return super.tabComplete(commandSender, alias, args);
        }
        List<String> complete = this.commandTabComplete.complete(commandSender, alias, args);
        if(complete == null) {
            return super.tabComplete(commandSender, alias, args);
        }
        return complete.parallelStream().limit(15).collect(Collectors.toList());
    }

    @Override
    public Group getGroup() {
        return this.group;
    }

    @Override
    public void setOnlyGroup(Group group) {
        this.onlyGroup = true;
        this.group = group;
    }

    @Override
    public void setCommandIssuer(CommandIssuer commandIssuer) {
        this.commandIssuer = commandIssuer;
    }

    @Override
    public void setOnlyConsole(boolean flag) {
        this.onlyConsole = flag;
    }

    @Override
    public void setCooldown(String key, int seconds) {
        this.cooldown = seconds * 20;
        this.cooldownType = key;
    }

    @Override
    public Command getCommand() {
        return this;
    }

    @Override
    public void setMinimalGroup(Group group) {
        this.group = group;
        this.minimalGroup = true;
    }

    @Override
    public void setMinimalGroup(int level) {
        this.group = Group.getGroupByLevel(level);
    }

    @Override
    public void setCommandTabComplete(CommandTabComplete tabComplete) {
        this.commandTabComplete = tabComplete;
    }

    @Override
    public void setOnlyPlayers(boolean flag) {
        this.onlyPlayers = flag;
    }

    @Override
    public void disable() {
        BukkitAPI.getCommandsAPI().disableCommand(this);
    }

    @Override
    public boolean execute(CommandSender commandSender, String commandName, String[] args) {
        boolean checkPlayer = commandSender instanceof Player;
        if(!checkPlayer && this.onlyPlayers) {
            commandSender.sendMessage(Lang.getMessage("COMMAND_ONLY_PLAYERS"));
            return false;
        }
        if(checkPlayer && this.onlyConsole) {
            commandSender.sendMessage(Lang.getMessage("COMMAND_ONLY_CONSOLE"));
            return false;
        }
        if(checkPlayer) {
            Player player = (Player)commandSender;
            if(!player.isOnline()) {
                return false;
            }
            BukkitGamer gamer = GamerManager.getGamer(player);
            if(gamer.getGroup().getLevel() < Group.ADMIN.getLevel()) {
                if(Cooldown.hasCooldown(player.getName(), this.getCooldownType())) {
                    int time = Cooldown.getCooldownLeft(player.getName(), this.getCooldownType());
                    gamer.sendMessageLocale("COOLDOWN", time);
                    return false;
                }
                Cooldown.addCooldown(player.getName(), this.getCooldownType(), this.cooldown);
            }
            //todo only group and minimal group adding
            if(gamer.getGroup().getLevel() < this.group.getLevel() && gamer.getGroup() != Group.ADMIN) {
                gamer.sendMessageLocale("NO_PERMISSIONS", this.group.getChatPrefix());
                return false;
            }
        }
        this.fixArgs(args);
        this.commandIssuer.execute(commandSender, commandName, args);
        return true;
    }

    //[] <> replacer
    private void fixArgs(String[] args) {
        for (int i = 0; i < args.length; ++i) {
            String arg = args[i];
            if(arg.length() > 2) {
                char c0 = arg.charAt(0);
                char cl = arg.charAt(arg.length() - 1);
                if((c0 == '[' && cl == ']') || (c0 == '<' && cl == '>')) {
                    args[i] = arg.substring(1, arg.length() - 1);
                }
            }
        }
    }
}
