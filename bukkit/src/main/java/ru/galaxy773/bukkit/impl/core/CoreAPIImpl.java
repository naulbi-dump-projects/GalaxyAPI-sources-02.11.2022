package ru.galaxy773.bukkit.impl.core;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import ru.galaxy773.bukkit.api.BukkitAPI;
import ru.galaxy773.bukkit.api.core.CoreAPI;
import ru.galaxy773.bukkit.api.gamer.BukkitGamer;
import ru.galaxy773.bukkit.api.gamer.GamerManager;
import ru.galaxy773.bukkit.api.utils.bukkit.BukkitUtil;
import ru.galaxy773.core.connector.CoreConnector;
import ru.galaxy773.core.connector.bukkit.BukkitConnector;
import ru.galaxy773.core.io.info.filter.ServerFilter;
import ru.galaxy773.core.io.packet.bukkit.BukkitPlayerDispatchCommand;
import ru.galaxy773.core.io.packet.bukkit.BukkitPlayerRedirect;
import ru.galaxy773.multiplatform.api.game.GameState;
import ru.galaxy773.multiplatform.api.locale.Lang;
import ru.galaxy773.multiplatform.api.utils.StringUtil;
import ru.galaxy773.multiplatform.api.utils.cooldown.Cooldown;

import java.io.File;

public class CoreAPIImpl implements CoreAPI {

    private final BukkitConnector connector = (BukkitConnector) CoreConnector.getInstance();
    @Getter
    @Setter
    private boolean restart;

    public CoreAPIImpl() {
        BukkitAPI.getPlaceholderAPI().registerArgumentedPlaceholder("%online:", server -> {
            String[] servers = server.split(",");
            int online = 0;
            for (String serverName : servers)
                online += this.getOnline(serverName);

            return StringUtil.getNumberFormat(online);
        });
    }

    @Override
    public String getServerName() {
        return this.connector.getServerName();
    }

    @Override
    public String getConfigDirectory() {
        return "/root/FixMine/build/" + connector.getServerName().split("-")[0] + "/config";
    }

    @Override
    public String getServerDirectory() {
        return "/root/FixMine/servers/" + connector.getServerName();
    }

    @Override
    public String getGameWorld() {
        String world = null;
        File folder = new File(getConfigDirectory());
        File[] listOfFiles = folder.listFiles();
        assert listOfFiles != null;
        for (File file : listOfFiles) {
            if (file.isDirectory() && !file.getName().equals("logs") && !file.getName().equals("lobby") && !file.getName().equals("plugins") && !file.getName().equals("timings") && !file.getName().equals("endlobby")) {
                world = file.getName();
                break;
            }
        }
        return world;
    }

    @Override
    public void registerGame(String mapName, boolean alwaysDay) {

    }

    @Override
    public void registerLobby(int time) {

    }

    @Override
    public void restart() {
        BukkitUtil.runTask(() -> {
            Bukkit.getOnlinePlayers().forEach(player -> player.kickPlayer(Lang.getMessage("ERROR_TO_LOBBY")));
            try {
                String world = getGameWorld();
                /*if (!Bukkit.unloadWorld(world, false) || this.restart) {
                    Bukkit.shutdown();
                } else {
                    FileUtils.cleanDirectory(new File(this.getServerDirectory() + "/lobby/playerdata"));
                    FileUtils.cleanDirectory(new File(this.getServerDirectory() + "/lobby/advancements"));
                    //GameModeScoreBoardTeam.resetBoard();
                    //CoreUtil.SCORE_BOARD_API.getActiveDefaultTag().clear();
                    GamerAPI.getGamers().clear();
                    //new WaitModule();
                }*/
            }
            catch (Exception ex) {
                ex.printStackTrace();
            }
            return;
        });
        GameState.setCurrent(GameState.WAITING);
        //BukkitUtil.callEvent(new GameChangeStateEvent(GameState.WAITING));
    }

    @Override
    public void sendToServer(Player player, String regex) {
        this.sendToServer(GamerManager.getGamer(player), regex);
    }

    @Override
    public void sendToServer(BukkitGamer gamer, @NonNull String regex) {
        if (Cooldown.hasCooldown(gamer.getName(), "redirect_players"))
            return;

        Cooldown.addCooldown(gamer.getName(), "redirect_players", 10);
        this.connector.sendPacket(new BukkitPlayerRedirect(gamer.getPlayerID(), regex, ServerFilter.REGEX));
    }

    @Override
    public void executeCommand(Player player, String command) {
        this.executeCommand(GamerManager.getGamer(player), command);
    }

    @Override
    public void executeCommand(BukkitGamer gamer, String command) {
        this.connector.sendPacket(new BukkitPlayerDispatchCommand(gamer.getPlayerID(), command));
    }

    @Override
    public int getCoreOnline() {
        return this.connector.getOnline();
    }

    @Override
    public int getOnline(String regex) {
        this.connector.addOnlineTask(regex);
        return this.connector.getOnline(regex);
    }
}
