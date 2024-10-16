package ru.galaxy773.lobby.hider;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.player.PlayerJoinEvent;
import ru.galaxy773.bukkit.api.event.gamer.async.AsyncGamerJoinEvent;
import ru.galaxy773.bukkit.api.gamer.BukkitGamer;
import ru.galaxy773.bukkit.api.gamer.GamerManager;
import ru.galaxy773.bukkit.api.utils.listener.GListener;
import ru.galaxy773.lobby.Lobby;
import ru.galaxy773.multiplatform.api.gamer.constants.SettingsType;

public class HiderListener extends GListener<Lobby> {

    public HiderListener(Lobby plugin) {
        super(plugin);
    }

    @EventHandler
    public void onJoin(AsyncGamerJoinEvent e) {
        BukkitGamer gamer = e.getGamer();
        Player player = gamer.getPlayer();
        HiderItem.giveToPlayer(player, gamer.getSetting(SettingsType.HIDER));
    }

    @EventHandler(priority=EventPriority.MONITOR)
    public void onJoin(PlayerJoinEvent e) {
        Player player = e.getPlayer();
        BukkitGamer gamer = GamerManager.getGamer(player);
        for (Player otherPlayer : Bukkit.getOnlinePlayers()) {
            if (player == otherPlayer)
                continue;

            BukkitGamer otherGamer = GamerManager.getGamer(otherPlayer);
            if (gamer.getSetting(SettingsType.HIDER))
                player.hidePlayer(otherPlayer);

            if (!otherGamer.getSetting(SettingsType.HIDER))
                continue;

            otherPlayer.hidePlayer(player);
        }
    }
}

