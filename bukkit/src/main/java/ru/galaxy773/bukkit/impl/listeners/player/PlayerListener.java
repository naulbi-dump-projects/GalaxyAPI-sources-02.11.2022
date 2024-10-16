package ru.galaxy773.bukkit.impl.listeners.player;

import org.bukkit.entity.Player;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.plugin.java.JavaPlugin;
import ru.galaxy773.bukkit.api.event.player.PlayerKillEvent;
import ru.galaxy773.bukkit.api.utils.bukkit.BukkitUtil;
import ru.galaxy773.bukkit.api.utils.bukkit.PlayerUtil;
import ru.galaxy773.bukkit.api.utils.listener.FastListener;

public class PlayerListener {

    public PlayerListener(JavaPlugin javaPlugin) {
        FastListener.create().easyEvent(PlayerDeathEvent.class, (e) -> {
            e.setDeathMessage(null);
            Player player = e.getEntity();
            Player killer = PlayerUtil.getDamager(e.getEntity().getKiller());
            if(killer != null) {
                BukkitUtil.callEvent(new PlayerKillEvent(player, killer));
            }
        }).register(javaPlugin);
    }
}
