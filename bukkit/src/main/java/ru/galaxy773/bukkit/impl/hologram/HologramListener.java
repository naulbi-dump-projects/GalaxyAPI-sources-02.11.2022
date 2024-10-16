package ru.galaxy773.bukkit.impl.hologram;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.player.PlayerQuitEvent;
import ru.galaxy773.bukkit.api.BukkitPlugin;
import ru.galaxy773.bukkit.api.entity.stand.CustomStand;
import ru.galaxy773.bukkit.api.event.player.PlayerInteractCustomStandEvent;
import ru.galaxy773.bukkit.api.event.player.PlayerInteractHologramEvent;
import ru.galaxy773.bukkit.api.hologram.Hologram;
import ru.galaxy773.bukkit.api.utils.bukkit.BukkitUtil;
import ru.galaxy773.bukkit.api.utils.listener.GListener;

public class HologramListener extends GListener<BukkitPlugin> {

    private final HologramManager hologramManager;

  	public HologramListener(HologramAPIImpl hologramAPI) {
        super(BukkitPlugin.getInstance());
        this.hologramManager = hologramAPI.getHologramManager();
    }
  	
    @EventHandler
    public void onPlayerInteractCustomStand(PlayerInteractCustomStandEvent e) {
        CustomStand stand = e.getStand();
        Player player = e.getPlayer();

        PlayerInteractCustomStandEvent.Action action = e.getAction();

        Hologram hologram = hologramManager.getHologramByStand().get(stand);
        if (hologram == null) {
            return;
        }
        /*if(hologram.getInteractAction() != null) {
        	hologram.getInteractAction().onClick(player, action == Action.RIGHT_CLICK ? ClickType.RIGHT : ClickType.LEFT);
        }*/
        PlayerInteractHologramEvent event = new PlayerInteractHologramEvent(player, hologram, action);
        BukkitUtil.callEvent(event);
    }
    
    @EventHandler(priority = EventPriority.LOWEST)
    public void onQuit(PlayerQuitEvent e) {
        Player player = e.getPlayer().getPlayer();
        if (player == null) {
            return;
        }

        hologramManager.getHolograms().forEach(hologram -> {
            hologram.removeTo(player);
            if (hologram.getOwner() != null && player.getName().equals(hologram.getOwner().getName()))
                hologram.remove();
        });
    }
}
