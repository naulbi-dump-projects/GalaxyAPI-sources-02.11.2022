package ru.galaxy773.bukkit.commands;

import org.bukkit.command.CommandSender;
import ru.galaxy773.bukkit.api.BukkitAPI;
import ru.galaxy773.bukkit.api.BukkitPlugin;
import ru.galaxy773.bukkit.api.commands.CommandIssuer;
import ru.galaxy773.bukkit.api.commands.CommandSource;
import ru.galaxy773.bukkit.api.gamer.BukkitGamer;
import ru.galaxy773.bukkit.api.gamer.GamerManager;
import ru.galaxy773.multiplatform.api.gamer.constants.Group;
import ru.galaxy773.multiplatform.api.gamer.constants.KeysType;
import ru.galaxy773.multiplatform.api.locale.Lang;
import ru.galaxy773.multiplatform.api.utils.StringUtil;
import ru.galaxy773.multiplatform.impl.loader.GlobalLoader;
import ru.galaxy773.multiplatform.impl.loader.PlayerInfoLoader;

import java.util.Arrays;

public class BoxesCommand implements CommandIssuer {

    public BoxesCommand() {
        CommandSource commandSource = BukkitAPI.getCommandsAPI().register(BukkitPlugin.getInstance(), "boxes", this, "box");
        commandSource.setOnlyGroup(Group.ADMIN);
    }

    @Override
    public void execute(CommandSender commandSender, String commandName, String[] args) {
        if (args.length == 0) {
            Lang.getList("BOXES_COMMAND_HELP").forEach(commandSender::sendMessage);
            return;
        }
        String lowerCase = args[0].toLowerCase();
        switch (lowerCase) {
            case "get": {
                this.getBoxes(commandSender, args);
                break;
            }
            case "add":
            case "give": {
                this.giveBoxes(commandSender, args);
                break;
            }
            case "remove":
            case "take": {
                this.takeBoxes(commandSender, args);
                break;
            }
            default: {
                Lang.getList("BOXES_COMMAND_HELP").forEach(commandSender::sendMessage);
                break;
            }
        }
    }

    private void giveBoxes(CommandSender sender, String[] args) {
        if(args.length < 4) {
            sender.sendMessage(Lang.getMessage("BOXES_COMMAND_GIVE_USE"));
            return;
        }
        if(!StringUtil.isInteger(args[3])) {
            sender.sendMessage(Lang.getMessage("BOXES_NOT_NUMERIC"));
            return;
        }
        KeysType keysType = Arrays.stream(KeysType.values())
                .filter(keyType -> keyType.name().equalsIgnoreCase(args[2]))
                .findFirst()
                .orElse(null);
        if(keysType == null) {
            sender.sendMessage(Lang.getMessage("BOXES_NOT_FOUND"));
            return;
        }
        BukkitGamer gamer = GamerManager.getGamer(args[1]);
        if (gamer == null) {
            int playerID = GlobalLoader.getPlayerID(args[1]);
            if (playerID == -1) {
                return;
            }
            int value = PlayerInfoLoader.getKeys(playerID).getOrDefault(keysType, 0);
            PlayerInfoLoader.setKeys(playerID, keysType, value + Integer.parseInt(args[3]) );
        } else {
            gamer.setKeys(keysType, gamer.getKeys(keysType) + Integer.parseInt(args[3]), true);
        }
        sender.sendMessage(Lang.getMessage("BOXES_GIVE_SUCCESS", args[1], args[3], args[2]));
    }

    private void getBoxes(CommandSender sender, String[] args) {
        if(args.length < 3) {
            sender.sendMessage(Lang.getMessage("BOXES_COMMAND_GET_USE"));
            return;
        }
        KeysType keysType = Arrays.stream(KeysType.values())
                .filter(keyType -> keyType.name().equalsIgnoreCase(args[2]))
                .findFirst()
                .orElse(null);
        if(keysType == null) {
            sender.sendMessage(Lang.getMessage("BOXES_NOT_FOUND"));
            return;
        }
        BukkitGamer gamer = GamerManager.getGamer(args[1]);
        int value = 0;
        if (gamer == null) {
            int playerID = GlobalLoader.getPlayerID(args[1]);
            if (playerID == -1) {
                return;
            }
            value = PlayerInfoLoader.getKeys(playerID).getOrDefault(keysType, 0);
        } else {
            value = gamer.getKeys(keysType);
        }
        sender.sendMessage(Lang.getMessage("BOXES_GET_SUCCESS", args[1], args[2], value));
    }

    private void takeBoxes(CommandSender sender, String[] args) {
        if(args.length < 4) {
            sender.sendMessage(Lang.getMessage("BOXES_COMMAND_REMOVE_USE"));
            return;
        }
        if(!StringUtil.isInteger(args[3])) {
            sender.sendMessage(Lang.getMessage("BOXES_NOT_NUMERIC"));
            return;
        }
        KeysType keysType = Arrays.stream(KeysType.values())
                .filter(keyType -> keyType.name().equalsIgnoreCase(args[2]))
                .findFirst()
                .orElse(null);
        if(keysType == null) {
            sender.sendMessage(Lang.getMessage("BOXES_NOT_FOUND"));
            return;
        }
        BukkitGamer gamer = GamerManager.getGamer(args[1]);
        if (gamer == null) {
            int playerID = GlobalLoader.getPlayerID(args[1]);
            if (playerID == -1) {
                return;
            }
            int value = PlayerInfoLoader.getKeys(playerID).getOrDefault(keysType, 0);
            PlayerInfoLoader.setKeys(playerID, keysType, value - Integer.parseInt(args[3]) );
        } else {
            gamer.setKeys(keysType, gamer.getKeys(keysType) - Integer.parseInt(args[3]), true);
        }
        sender.sendMessage(Lang.getMessage("BOXES_REMOVE_SUCCESS", args[1], args[3], args[2]));
    }
}
