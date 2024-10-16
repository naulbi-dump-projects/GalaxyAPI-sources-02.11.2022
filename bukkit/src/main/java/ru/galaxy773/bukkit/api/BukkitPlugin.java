package ru.galaxy773.bukkit.api;

import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.plugin.java.JavaPlugin;
import ru.galaxy773.bukkit.api.game.GameType;
import ru.galaxy773.bukkit.api.game.SubType;
import ru.galaxy773.bukkit.api.utils.bukkit.EmptyWorldGenerator;
import ru.galaxy773.bukkit.commands.ApiCommands;
import ru.galaxy773.bukkit.commands.BoxesCommand;
import ru.galaxy773.bukkit.commands.CoinsCommand;
import ru.galaxy773.bukkit.commands.GoldCommand;
import ru.galaxy773.bukkit.commands.donate.FireworkCommand;
import ru.galaxy773.bukkit.commands.profile.*;
import ru.galaxy773.bukkit.impl.listeners.gamer.GamerListener;
import ru.galaxy773.bukkit.impl.listeners.gamer.SettingsListener;
import ru.galaxy773.bukkit.impl.listeners.networking.BungeeMessageListener;
import ru.galaxy773.bukkit.impl.listeners.player.PlayerListener;
import ru.galaxy773.bukkit.nms.api.NmsAPI;
import ru.galaxy773.multiplatform.impl.loader.GlobalLoader;
import ru.galaxy773.multiplatform.impl.loader.PlayerInfoLoader;

import java.io.File;
import java.io.IOException;

public class BukkitPlugin extends JavaPlugin {

    @Getter
    private static BukkitPlugin instance;
    @Getter
    private EmptyWorldGenerator generator;

    @Override
    public void onLoad() {
        instance = this;
    }

    @Override
    public void onEnable() {
        this.generator = new EmptyWorldGenerator();
        if (!this.getDataFolder().exists())
            this.getDataFolder().mkdir();

        this.registerType();

        NmsAPI.init(this);

        this.registerListeners();
        this.registerCommands();
    }

    private void registerCommands() {
        new ApiCommands(this);
        new BoxesCommand();
        new CoinsCommand();
        new GoldCommand();
        new DonateMenuCommand();
        new FastMessagesCommand();
        new JoinMessagesCommand();
        new LevelCommand();
        new ProfileCommand();
        new InformationCommand();
        new QuitMessagesCommand();
        new SettingsCommand();
        new TitulsCommand();
        //new GrantCommand(this);
        new FireworkCommand(this);
    }

    private void registerListeners() {
        new GamerListener(this);
        new SettingsListener(this);
        new PlayerListener(this);
        new BungeeMessageListener(this);
    }

    private void registerType() {
        String serverName = null;
        try {
            serverName = new File("").getCanonicalFile().getName();
        } catch (IOException e) {
            e.printStackTrace();
        }
        assert serverName != null;
        String[] splittedServerName = serverName.split("-");
        GameType gameType = GameType.tryGet(splittedServerName[0]);
        //hub
        //auth
        //lobby-lw | lw-lobby
        //lw-doubles-1
        //lw-solo-1
        //survival
        //oneblock
        if (gameType == null) {
            Bukkit.shutdown();
            throw new RuntimeException("GameType not found, rename server folder to correct game type!");
        }
        SubType subType = SubType.tryGet(serverName);
        if (subType == null) {
            Bukkit.shutdown();
            throw new RuntimeException("SubType not found, rename server folder to correct gameType-typeGame!");
        }
        GameType.setCurrent(gameType);
        SubType.setCurrent(subType);
    }

    @Override
    public void onDisable() {
        Bukkit.getWorlds().forEach(World::save);
        GlobalLoader.getMySql().close();
        PlayerInfoLoader.getMySql().close();
        Bukkit.shutdown();
    }
}
