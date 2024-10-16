package ru.galaxy773.bukkit.impl.hologram;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.server.PluginDisableEvent;
import ru.galaxy773.bukkit.api.BukkitPlugin;
import ru.galaxy773.bukkit.api.utils.listener.GListener;

public class HologramAPIListener extends GListener<BukkitPlugin> {

	private final HologramManager hologramManager;
	
	protected HologramAPIListener(HologramManager hologramManager) {
		super(BukkitPlugin.getInstance());
		this.hologramManager = hologramManager;
	}

  	//todo проверить
  	@EventHandler(priority = EventPriority.HIGHEST)
  	public void onPluginDisable(PluginDisableEvent e) {
  		hologramManager.getHolograms().forEach(hologram -> {
  			if(hologram.getPlugin().getName().equals(e.getPlugin().getName())) {
  				hologramManager.removeHologram(hologram);
  			}
  		});
  	}
}
