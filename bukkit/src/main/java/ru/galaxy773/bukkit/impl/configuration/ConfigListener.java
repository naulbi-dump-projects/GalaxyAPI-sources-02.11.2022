package ru.galaxy773.bukkit.impl.configuration;

import org.bukkit.event.server.PluginDisableEvent;
import org.bukkit.plugin.java.JavaPlugin;
import ru.galaxy773.bukkit.api.BukkitPlugin;
import ru.galaxy773.bukkit.api.utils.listener.FastListener;

public class ConfigListener {

	private final ConfigManager configManager;
	
	protected ConfigListener(ConfigManager configManager) {
		this.configManager = configManager;
		FastListener.create().easyEvent(PluginDisableEvent.class, (event) -> {
			configManager.getConfigs().remove((JavaPlugin) event.getPlugin());
		}).register(BukkitPlugin.getInstance());
	}
}
