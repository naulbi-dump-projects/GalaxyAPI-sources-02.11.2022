package ru.galaxy773.bukkit.impl.configuration;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ConfigManager {

	private final Map<JavaPlugin, ConcurrentHashMap<String, FileConfiguration>> configs;
	
	public ConfigManager() {
		configs = new ConcurrentHashMap<JavaPlugin, ConcurrentHashMap<String, FileConfiguration>>();
	}
	
	@SuppressWarnings("serial")
	public void addConfig(JavaPlugin javaPlugin, String configName, FileConfiguration FileConfiguration) {
		if(configs.containsKey(javaPlugin)) {
			configs.get(javaPlugin).put(configName, FileConfiguration);
			return;
		}
		configs.put(javaPlugin, new ConcurrentHashMap<String, FileConfiguration>() { 
			{
				put(configName, FileConfiguration);
			}
		});
	}
	
	public void removeConfig(JavaPlugin javaPlugin, String configName) {
		configs.get(javaPlugin).remove(configName);
		if(configs.get(javaPlugin).isEmpty()) {
			configs.remove(javaPlugin);
		}
	}
	
	public FileConfiguration getConfig(JavaPlugin javaPlugin, String configName) {
		return configs.get(javaPlugin).get(configName);
	}
	
	public Map<JavaPlugin, ConcurrentHashMap<String, FileConfiguration>> getConfigs() {
		return configs;
	}
}
