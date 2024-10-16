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
import ru.galaxy773.multiplatform.impl.loader.GlobalLoader;
import ru.galaxy773.multiplatform.impl.loader.PlayerInfoLoader;

public class CoinsCommand implements CommandIssuer {

    public CoinsCommand() {
        BukkitAPI.getCommandsAPI().register(BukkitPlugin.getInstance(), "coins", this, "coin");
    }

    @Override
    public void execute(CommandSender commandSender, String commandName, String[] args) {
        if (commandSender instanceof Player) {
            BukkitGamer gamer = GamerManager.getGamer(commandSender.getName());
            if (args.length == 0) {
                gamer.sendMessageLocale("COINS_INFO", StringUtil.getNumberFormat(gamer.getCoins()));
                return;
            }
            if (!gamer.hasChildGroup(Group.ADMIN)) {
                gamer.sendMessageLocale("NO_PERMISSIONS");
                return;
            }
        }
        if (args.length == 0) {
            Lang.getList("COINS_HELP").forEach(commandSender::sendMessage);
            return;
        }
        String lowerCase = args[0].toLowerCase();
        switch (lowerCase) {
            case "get": {
                this.getCoins(commandSender, args);
                break;
            }
            case "add":
            case "give": {
                this.giveCoins(commandSender, args);
                break;
            }
            case "set": {
                this.setCoins(commandSender, args);
                break;
            }
            case "remove":
            case "take": {
                this.takeCoins(commandSender, args);
                break;
            }
            default: {
                Lang.getList("COINS_HELP").forEach(commandSender::sendMessage);
                break;
            }
        }
    }

    private void getCoins(CommandSender commandSender, String[] args) {
        if (args.length < 2) {
            commandSender.sendMessage(Lang.getMessage("COINS_GET_USE"));
            return;
        }
        int playerID = GlobalLoader.getPlayerID(args[1]);
        if (playerID == -1) {
            return;
        }
        commandSender.sendMessage(Lang.getMessage("COINS_GET_SUCCESS", args[1], StringUtil.getNumberFormat(PlayerInfoLoader.getNetworking(playerID).get(1))));
    }

    private void giveCoins(CommandSender commandSender, String[] args) {
        if (args.length < 3) {
            commandSender.sendMessage(Lang.getMessage("COINS_GIVE_USE"));
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
            PlayerInfoLoader.setNetworkingValue(playerID, NetworkingType.COINS, PlayerInfoLoader.getNetworking(playerID).get(1) + Integer.parseInt(args[2]));
        }
        else {
            gamer.setCoins(gamer.getCoins() + Integer.parseInt(args[2]), true);
        }
        commandSender.sendMessage(Lang.getMessage("COINS_GIVE_SUCCESS", args[1], args[2]));
    }

    private void setCoins(CommandSender commandSender, String[] args) {
        if (args.length < 3) {
            commandSender.sendMessage(Lang.getMessage("COINS_SET_USE"));
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
            PlayerInfoLoader.setNetworkingValue(playerID, NetworkingType.COINS, Integer.parseInt(args[2]));
        }
        else {
            gamer.setCoins(Integer.parseInt(args[2]), true);
        }
        commandSender.sendMessage(Lang.getMessage("COINS_SET_SUCCESS", args[1], args[2]));
    }

    private void takeCoins(CommandSender commandSender, String[] args) {
        if (args.length < 3) {
            commandSender.sendMessage(Lang.getMessage("COINS_TAKE_USE"));
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
            PlayerInfoLoader.setNetworkingValue(playerID, NetworkingType.COINS, (value - Integer.parseInt(args[2]) < 0) ? 0 : (Integer.parseInt(args[2]) - value));
        }
        else {
            gamer.setCoins(gamer.getCoins() - Integer.parseInt(args[2]), true);
        }
        commandSender.sendMessage(Lang.getMessage("COINS_TAKE_SUCCESS", args[1], args[2]));
    }
}
