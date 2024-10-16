package ru.galaxy773.bukkit.api.tops.armorstand;

import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.java.JavaPlugin;
import ru.galaxy773.bukkit.api.event.gamer.async.AsyncGamerJoinEvent;
import ru.galaxy773.bukkit.api.event.player.PlayerInteractCustomStandEvent.Action;
import ru.galaxy773.bukkit.api.event.player.PlayerInteractHologramEvent;
import ru.galaxy773.bukkit.api.hologram.Hologram;
import ru.galaxy773.bukkit.api.utils.listener.GListener;

public final class StandTopListener extends GListener<JavaPlugin> {

    private final TopManager manager;

    StandTopListener(TopManager standTopManager) {
        super(standTopManager.getJavaPlugin());
        this.manager = standTopManager;
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onJoinAsync(AsyncGamerJoinEvent e) {
        if (manager.size() < 1) {
            return;
        }
        manager.getStandPlayerStorage().addPlayer(new StandPlayer(manager, e.getGamer().getPlayer()));
    }
    
    @EventHandler
    public void onRemove(PlayerQuitEvent e) {
        manager.getStandTopSelection().removePlayer(e.getPlayer().getName());
        manager.getStandPlayerStorage().removePlayer(e.getPlayer().getName());
    }
    
    @EventHandler(priority = EventPriority.MONITOR)
    public void onClick(PlayerInteractHologramEvent e) {
    	if(e.getAction() == Action.LEFT_CLICK) {
    		return;
    	}
        Hologram hologram = e.getHologram();
        Player gamer = e.getPlayer();
        if (manager.size() <= 1) {
            return;
        }
        StandPlayer standPlayer = manager.getStandPlayerStorage().getPlayer(gamer);
        if (standPlayer == null) {
            return;
        }
        Top topType = standPlayer.getTopType();
        Hologram mainHologram = topType.getHologramMiddle();
        if (mainHologram == null || mainHologram != hologram) {
            return;
        }
        standPlayer.changeSelected();
        gamer.playSound(gamer.getLocation(), Sound.UI_BUTTON_CLICK, 1f, 1f);
    }
}
