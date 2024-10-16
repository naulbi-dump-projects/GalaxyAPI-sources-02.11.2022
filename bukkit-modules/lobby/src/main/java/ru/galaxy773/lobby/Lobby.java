package ru.galaxy773.lobby;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import ru.galaxy773.bukkit.impl.listeners.lobby.LobbyGuardListener;
import ru.galaxy773.lobby.board.BoardListener;
import ru.galaxy773.lobby.bossbar.BossBarListener;
import ru.galaxy773.lobby.commands.LobbyCommands;
import ru.galaxy773.lobby.config.*;
import ru.galaxy773.lobby.customitems.CustomItemListener;
import ru.galaxy773.lobby.hider.HiderListener;
import ru.galaxy773.lobby.parkour.ParkourLoader;
import ru.galaxy773.lobby.presents.PresentsLoader;
import ru.galaxy773.multiplatform.impl.sql.MySqlDatabase;

import java.util.HashMap;
import java.util.Map;

public class Lobby
extends JavaPlugin {

    private static Lobby instance;
    private MySqlDatabase mySql;
    private final Map<String, LobbyConfig> configs = new HashMap<String, LobbyConfig>();

    public void onLoad() {
        instance = this;
    }

    public void onEnable() {
        this.mySql = MySqlDatabase.builder()
                .host(MySqlDatabase.HOST)
                .database("fm-lobby")
                .user(MySqlDatabase.USER)
                .password(MySqlDatabase.PASSWORD)
                .build();
        PresentsLoader.initialize(this.mySql);
        ParkourLoader.initialize(this.mySql);
        GameConfig gameConfig = this.initConfig(GameConfig.class);
        this.initConfig(TopConfig.class);
        this.initConfig(ParkourConfig.class);
        if (gameConfig.isPresents()) {
            this.initConfig(PresentsConfig.class);
        }
        new LobbyCommands(this, gameConfig);
        new BossBarListener(this);
        new HiderListener(this);
        new CustomItemListener(this);
        new BoardListener(this);
        new LobbyGuardListener(this, Bukkit.getWorlds().get(0), 6000);
    }

    /*public void onDisable() {
        this.mySql.close();
    }*/

    public <T extends LobbyConfig> T initConfig(Class<T> configClass) {
        String name = configClass.getSimpleName().toLowerCase();
        LobbyConfig config = null;
        try {
            config = configClass.getConstructor(new Class[0]).newInstance();
            config.load();
            config.init();
            this.configs.put(name, config);
        }
        catch (Exception ignored) { }
        return (T)config;
    }

    public <T extends LobbyConfig> T getConfig(Class<T> configClass) {
        String name = configClass.getSimpleName().toLowerCase();
        return (T)this.configs.get(name);
    }

    public void reloadConfig() {
        this.configs.values().forEach(LobbyConfig::load);
    }

    public static Lobby getInstance() {
        return instance;
    }
}

