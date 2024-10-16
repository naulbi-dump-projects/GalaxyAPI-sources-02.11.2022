package ru.galaxy773.bukkit.impl.configuration;

import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;
import ru.galaxy773.bukkit.api.configuration.ConfigAPI;

import java.io.File;
import java.io.IOException;

public class ConfigAPIImpl implements ConfigAPI {

	private static final ConfigManager CONFIG_MANAGER = new ConfigManager();
	
	public ConfigAPIImpl() {
		new ConfigListener(CONFIG_MANAGER);
	}
	
	@Override
	public FileConfiguration loadConfig(JavaPlugin javaPlugin, String configName) {
		if (javaPlugin.getDataFolder().exists()) {
    		javaPlugin.getDataFolder().mkdir();
        }
		FileConfiguration config = null;
		try {
			File file = new File(javaPlugin.getDataFolder(), configName);
			if(!file.exists()) {
				javaPlugin.saveResource(configName, false);
			}
			config = YamlConfiguration.loadConfiguration(file);
			config.save(file);
		} catch (IOException ex) {
			ex.printStackTrace();
		}
		CONFIG_MANAGER.addConfig(javaPlugin, configName, config);
		return config;
	}
	
	@Override
	public FileConfiguration getConfig(JavaPlugin javaPlugin, String configName) {
		return CONFIG_MANAGER.getConfig(javaPlugin, configName);
	}

	@Override
	public void saveConfig(JavaPlugin javaPlugin, String configName) {
		File file = new File(javaPlugin.getDataFolder(), configName);
		try {
			if(!file.exists()) {
				file.createNewFile();
			}
    		CONFIG_MANAGER.getConfig(javaPlugin, configName).save(file);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void unloadConfig(JavaPlugin javaPlugin, String configName) {
		CONFIG_MANAGER.removeConfig(javaPlugin, configName);
	}

	@Override
	public void reloadConfig(JavaPlugin javaPlugin, String configName) {
		File file = new File(javaPlugin.getDataFolder(), configName);
		try {
			if(!file.exists()) {
				file.createNewFile();
			}
    		CONFIG_MANAGER.getConfig(javaPlugin, configName).load(file);
		} catch (IOException | InvalidConfigurationException e) {
			e.printStackTrace();
		}
	}
}
