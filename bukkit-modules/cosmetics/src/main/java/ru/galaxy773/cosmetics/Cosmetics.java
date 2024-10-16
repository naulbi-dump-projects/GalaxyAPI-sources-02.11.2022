package ru.galaxy773.cosmetics;

import org.bukkit.event.EventPriority;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.java.JavaPlugin;
import ru.galaxy773.bukkit.api.BukkitAPI;
import ru.galaxy773.bukkit.api.event.gamer.async.AsyncGamerJoinEvent;
import ru.galaxy773.bukkit.api.gui.GuiManager;
import ru.galaxy773.bukkit.api.utils.listener.FastEvent;
import ru.galaxy773.bukkit.api.utils.listener.FastListener;
import ru.galaxy773.bukkit.impl.gui.types.AbstractGui;
import ru.galaxy773.cosmetics.api.CosmeticsAPI;
import ru.galaxy773.cosmetics.api.manager.CosmeticManager;
import ru.galaxy773.cosmetics.commands.EffectsCommand;
import ru.galaxy773.cosmetics.guis.ArrowsEffectGui;
import ru.galaxy773.cosmetics.guis.CritsEffectGui;
import ru.galaxy773.cosmetics.guis.EffectsMainGui;
import ru.galaxy773.cosmetics.guis.KillsEffectGui;
import ru.galaxy773.cosmetics.listeners.EffectsListener;
import ru.galaxy773.cosmetics.sql.CosmeticLoader;

public class Cosmetics
extends JavaPlugin {
    private static Cosmetics instance;

    public void onLoad() {
        instance = this;
    }

    public void onEnable() {
        this.registerGuis();
        new EffectsListener(this);
        new EffectsCommand();
        CosmeticManager cosmeticManager = CosmeticsAPI.getCosmeticManager();
        FastListener.create()
                .event(FastEvent.builder(AsyncGamerJoinEvent.class)
                        .priority(EventPriority.LOWEST)
                        .handler(event -> cosmeticManager.loadPlayer(event.getGamer().getPlayer()))
                        .build())
                .easyEvent(PlayerQuitEvent.class, event -> cosmeticManager.unloadPlayer(event.getPlayer()))
                .register(this);
    }

    public void onDisable() {
        CosmeticLoader.getMySql().close();
        this.unregisterGuis();
    }

    private void registerGuis() {
        GuiManager<AbstractGui<?>> guiManager = BukkitAPI.getGuiManager();
        guiManager.createGui(EffectsMainGui.class);
        guiManager.createGui(ArrowsEffectGui.class);
        guiManager.createGui(CritsEffectGui.class);
        guiManager.createGui(KillsEffectGui.class);
    }

    private void unregisterGuis() {
        GuiManager<AbstractGui<?>> guiManager = BukkitAPI.getGuiManager();
        guiManager.removeGui(EffectsMainGui.class);
        guiManager.removeGui(ArrowsEffectGui.class);
        guiManager.removeGui(CritsEffectGui.class);
        guiManager.removeGui(KillsEffectGui.class);
    }

    public static Cosmetics getInstance() {
        return instance;
    }
}

