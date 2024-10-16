package ru.galaxy773.bukkit.api.configuration;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

public interface ConfigAPI {
	
	public FileConfiguration loadConfig(JavaPlugin javaPlugin, String configName);
	
	public FileConfiguration getConfig(JavaPlugin javaPlugin, String configName);
	
	public void saveConfig(JavaPlugin javaPlugin, String configName);
	
	public void unloadConfig(JavaPlugin javaPlugin, String configName);
	
	public void reloadConfig(JavaPlugin javaPlugin, String configName);
}
