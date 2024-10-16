package ru.galaxy773.bukkit.impl.scoreboard.board;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import ru.galaxy773.bukkit.api.BukkitPlugin;
import ru.galaxy773.bukkit.api.event.gamer.async.AsyncGamerQuitEvent;
import ru.galaxy773.bukkit.api.utils.listener.GListener;
import ru.galaxy773.bukkit.impl.scoreboard.ScoreBoardAPIImpl;

public class BoardListener extends GListener<BukkitPlugin> {

    private final ScoreBoardAPIImpl scoreBoardAPI;

    public BoardListener(ScoreBoardAPIImpl scoreBoardAPI) {
        super(BukkitPlugin.getInstance());
        this.scoreBoardAPI = scoreBoardAPI;
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onPlayerQuit(AsyncGamerQuitEvent e) {
        Player player = e.getGamer().getPlayer();
        if (player == null)
            return;

        scoreBoardAPI.removeBoard(player);
    }

}
