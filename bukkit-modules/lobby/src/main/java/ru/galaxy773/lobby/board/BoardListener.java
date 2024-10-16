package ru.galaxy773.lobby.board;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import ru.galaxy773.bukkit.api.event.gamer.GamerChangeSettingEvent;
import ru.galaxy773.bukkit.api.event.gamer.async.AsyncGamerJoinEvent;
import ru.galaxy773.bukkit.api.event.gamer.async.AsyncGamerQuitEvent;
import ru.galaxy773.bukkit.api.gamer.BukkitGamer;
import ru.galaxy773.bukkit.api.utils.bukkit.BukkitUtil;
import ru.galaxy773.bukkit.api.utils.listener.GListener;
import ru.galaxy773.lobby.Lobby;
import ru.galaxy773.multiplatform.api.gamer.constants.SettingsType;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class BoardListener extends GListener<Lobby> {

    private final Map<String, LobbyBoard> boards = new ConcurrentHashMap<>();

    public BoardListener(Lobby plugin) {
        super(plugin);
    }

    @EventHandler(priority=EventPriority.LOWEST)
    private void onAsyncJoin(AsyncGamerJoinEvent event) {
        BukkitGamer gamer = event.getGamer();
        if (!gamer.getSetting(SettingsType.SCOREBOARD))
            return;

        MainBoard mainBoard = new MainBoard(gamer.getPlayer());
        mainBoard.show();
        this.boards.put(gamer.getName(), mainBoard);
    }

    @EventHandler
    private void onQuit(AsyncGamerQuitEvent event) {
        if (!event.getGamer().getSetting(SettingsType.SCOREBOARD)) {
            return;
        }
        this.boards.remove(event.getGamer().getName()).remove();
    }

    @EventHandler(priority=EventPriority.HIGHEST, ignoreCancelled=true)
    private void onBoard(GamerChangeSettingEvent event) {
        if (event.getSetting() != SettingsType.SCOREBOARD) {
            return;
        }
        BukkitGamer gamer = event.getGamer();
        BukkitUtil.runTaskAsync(() -> {
            if (event.isEnable()) {
                MainBoard mainBoard = new MainBoard(gamer.getPlayer());
                mainBoard.show();
                this.boards.put(gamer.getName(), mainBoard);
            } else {
                this.boards.remove(gamer.getName()).remove();
            }
        });
    }
}

