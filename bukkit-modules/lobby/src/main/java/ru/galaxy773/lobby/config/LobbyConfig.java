package ru.galaxy773.lobby.config;

import org.bukkit.configuration.file.FileConfiguration;
import ru.galaxy773.bukkit.api.BukkitAPI;
import ru.galaxy773.bukkit.api.configuration.ConfigAPI;
import ru.galaxy773.lobby.Lobby;

public abstract class LobbyConfig {

    private static final ConfigAPI CONFIG_API = BukkitAPI.getConfigAPI();
    protected static final Lobby JAVA_PLUGIN = Lobby.getInstance();
    protected FileConfiguration config;

    public LobbyConfig(String configName) {
        this.config = CONFIG_API.loadConfig(JAVA_PLUGIN, configName);
    }

    public abstract void load();

    public abstract void init();

    public FileConfiguration getConfig() {
        return this.config;
    }
}

