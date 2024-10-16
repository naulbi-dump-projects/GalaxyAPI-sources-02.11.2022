package ru.galaxy773.bukkit.impl.scoreboard.objective;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import ru.galaxy773.bukkit.api.BukkitPlugin;
import ru.galaxy773.bukkit.api.event.gamer.async.AsyncGamerJoinEvent;
import ru.galaxy773.bukkit.api.event.gamer.async.AsyncGamerQuitEvent;
import ru.galaxy773.bukkit.api.utils.listener.GListener;
import ru.galaxy773.bukkit.impl.scoreboard.ScoreBoardAPIImpl;
import ru.galaxy773.bukkit.nms.api.NmsAPI;
import ru.galaxy773.bukkit.nms.interfaces.packet.PacketContainer;
import ru.galaxy773.bukkit.nms.scoreboard.GScore;
import ru.galaxy773.bukkit.nms.scoreboard.ScoreboardAction;

public class ObjectiveListener extends GListener<BukkitPlugin> {

    private final PacketContainer packetContainer = NmsAPI.getManager().getPacketContainer();
    private final ObjectiveManager objectiveManager;

    public ObjectiveListener(ScoreBoardAPIImpl scoreBoardAPI) {
        super(BukkitPlugin.getInstance());
        this.objectiveManager = scoreBoardAPI.getObjectiveManager();
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onJoin(AsyncGamerJoinEvent e) {
        Player player = e.getGamer().getPlayer();
        if (player == null || !player.isOnline()) {
            return;
        }
        for (CraftObjective craftObjective : objectiveManager.getObjectives()) {
            if (craftObjective.isPublic()) {
                craftObjective.showTo(player);
            }

            for (String name : craftObjective.getScores().keySet()) {
                Player all = Bukkit.getPlayerExact(name);
                if (all == null || !all.isOnline())
                    continue;

                int score = craftObjective.getScores().get(name);
                GScore dScore = new GScore(all.getName(), craftObjective.getGObjective(), score);
                packetContainer.getScoreboardScorePacket(dScore, ScoreboardAction.CHANGE).sendPacket(player);
            }
        }
    }

    @EventHandler
    public void onQuit(AsyncGamerQuitEvent e) {
        Player player = e.getGamer().getPlayer();
        String name = e.getGamer().getName();
        for (CraftObjective craftObjective : objectiveManager.getObjectives()) {
            if (craftObjective.getOwner() != null && craftObjective.getOwner().getName().equalsIgnoreCase(name)) {
                craftObjective.remove();
                continue;
            }
            craftObjective.removeScore(name);
            craftObjective.removeTo(player);
        }
    }
}
