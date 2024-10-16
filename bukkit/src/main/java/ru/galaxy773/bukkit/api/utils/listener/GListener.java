package ru.galaxy773.bukkit.api.utils.listener;

import org.bukkit.Bukkit;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;

public abstract class GListener<T extends Plugin> implements Listener {

    protected GListener(T plugin) {
        Bukkit.getPluginManager().registerEvents(this, plugin);
    }

    public void unregisterListener() {
        HandlerList.unregisterAll(this);
    }
}
