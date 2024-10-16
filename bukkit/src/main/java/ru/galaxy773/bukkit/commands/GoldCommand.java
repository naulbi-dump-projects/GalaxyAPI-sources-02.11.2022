package ru.galaxy773.bukkit.commands;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import ru.galaxy773.bukkit.api.BukkitAPI;
import ru.galaxy773.bukkit.api.BukkitPlugin;
import ru.galaxy773.bukkit.api.commands.CommandIssuer;
import ru.galaxy773.bukkit.api.gamer.BukkitGamer;
import ru.galaxy773.bukkit.api.gamer.GamerManager;
import ru.galaxy773.multiplatform.api.gamer.constants.Group;
import ru.galaxy773.multiplatform.api.gamer.data.NetworkingType;
import ru.galaxy773.multiplatform.api.locale.Lang;
import ru.galaxy773.multiplatform.api.utils.StringUtil;
import ru.galaxy773.multiplatform.impl.gamer.sections.NetworkingSection;
import ru.galaxy773.multiplatform.impl.loader.GlobalLoader;
import ru.galaxy773.multiplatform.impl.loader.PlayerInfoLoader;

public class GoldCommand implements CommandIssuer {

    public GoldCommand() {
        BukkitAPI.getCommandsAPI().register(BukkitPlugin.getInstance(), "gold", this, "золото");
    }

    @Override
    public void execute(CommandSender commandSender, String commandName, String[] args) {
        if (commandSender instanceof Player) {
            BukkitGamer gamer = GamerManager.getGamer(commandSender.getName());
            if (args.length == 0) {
                gamer.sendMessageLocale("GOLD_INFO", StringUtil.getNumberFormat(gamer.getGold()));
                return;
            }
            if (!gamer.hasChildGroup(Group.ADMIN)) {
                gamer.sendMessageLocale("NO_PERMISSIONS");
                return;
            }
        }
        if (args.length == 0) {
            Lang.getList("GOLD_HELP").forEach(commandSender::sendMessage);
            return;
        }
        String lowerCase = args[0].toLowerCase();
        switch (lowerCase) {
            case "get": {
                this.getGold(commandSender, args);
                break;
            }
            case "add":
            case "give": {
                this.giveGold(commandSender, args);
                break;
            }
            case "set": {
                this.setGold(commandSender, args);
                break;
            }
            case "remove":
            case "take": {
                this.takeGold(commandSender, args);
                break;
            }
            default: {
                Lang.getList("GOLD_HELP").forEach(commandSender::sendMessage);
                break;
            }
        }
    }

    private void getGold(CommandSender commandSender, String[] args) {
        if (args.length < 2) {
            commandSender.sendMessage(Lang.getMessage("GOLD_GET_USE"));
            return;
        }
        int playerID = GlobalLoader.getPlayerID(args[1]);
        if (playerID == -1) {
            return;
        }
        commandSender.sendMessage(Lang.getMessage("GOLD_GET_SUCCESS", args[1], StringUtil.getNumberFormat(PlayerInfoLoader.getNetworking(playerID).get(1))));
    }

    private void giveGold(CommandSender commandSender, String[] args) {
        if (args.length < 3) {
            commandSender.sendMessage(Lang.getMessage("GOLD_GIVE_USE"));
            return;
        }
        if (!StringUtil.isInteger(args[2])) {
            commandSender.sendMessage(Lang.getMessage("NOT_NUMERIC"));
            return;
        }
        BukkitGamer gamer = GamerManager.getGamer(args[1]);
        if (gamer == null) {
            int playerID = GlobalLoader.getPlayerID(args[1]);
            if (playerID == -1) {
                return;
            }
            PlayerInfoLoader.setNetworkingValue(playerID, NetworkingType.GOLD, PlayerInfoLoader.getNetworking(playerID).get(1) + Integer.parseInt(args[2]));
        }
        else {
            gamer.getSection(NetworkingSection.class).setGold(gamer.getGold() + Integer.parseInt(args[2]), true);
        }
        commandSender.sendMessage(Lang.getMessage("GOLD_GIVE_SUCCESS", args[1], args[2]));
    }

    private void setGold(CommandSender commandSender, String[] args) {
        if (args.length < 3) {
            commandSender.sendMessage(Lang.getMessage("GOLD_SET_USE"));
            return;
        }
        if (!StringUtil.isInteger(args[2])) {
            commandSender.sendMessage(Lang.getMessage("NOT_NUMERIC"));
            return;
        }
        BukkitGamer gamer = GamerManager.getGamer(args[1]);
        if (gamer == null) {
            int playerID = GlobalLoader.getPlayerID(args[1]);
            if (playerID == -1) {
                return;
            }
            PlayerInfoLoader.setNetworkingValue(playerID, NetworkingType.GOLD, Integer.parseInt(args[2]));
        }
        else {
            gamer.getSection(NetworkingSection.class).setGold(Integer.parseInt(args[2]), true);
        }
        commandSender.sendMessage(Lang.getMessage("GOLD_SET_SUCCESS", args[1], args[2]));
    }

    private void takeGold(CommandSender commandSender, String[] args) {
        if (args.length < 3) {
            commandSender.sendMessage(Lang.getMessage("GOLD_TAKE_USE"));
            return;
        }
        if (!StringUtil.isInteger(args[2])) {
            commandSender.sendMessage(Lang.getMessage("NOT_NUMERIC"));
            return;
        }
        BukkitGamer gamer = GamerManager.getGamer(args[1]);
        if (gamer == null) {
            int playerID = GlobalLoader.getPlayerID(args[1]);
            if (playerID == -1) {
                return;
            }
            int value = PlayerInfoLoader.getNetworking(playerID).get(1);
            PlayerInfoLoader.setNetworkingValue(playerID, NetworkingType.GOLD, (value - Integer.parseInt(args[2]) < 0) ? 0 : (Integer.parseInt(args[2]) - value));
        }
        else {
            gamer.getSection(NetworkingSection.class).setGold(gamer.getGold() - Integer.parseInt(args[2]), true);
        }
        commandSender.sendMessage(Lang.getMessage("GOLD_TAKE_SUCCESS", args[1], args[2]));
    }
}
