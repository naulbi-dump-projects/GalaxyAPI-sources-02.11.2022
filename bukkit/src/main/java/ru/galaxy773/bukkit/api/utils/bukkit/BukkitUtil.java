package ru.galaxy773.bukkit.api.utils.bukkit;

import lombok.experimental.UtilityClass;
import org.bukkit.Bukkit;
import org.bukkit.event.Event;
import org.bukkit.plugin.SimplePluginManager;
import ru.galaxy773.bukkit.api.BukkitPlugin;

@UtilityClass
public class BukkitUtil {

    public void runTaskAsync(Runnable runnable) {
        Bukkit.getScheduler().runTaskAsynchronously(BukkitPlugin.getInstance(), runnable);
    }

    public void runTaskLaterAsync(long ticks, Runnable runnable) {
        Bukkit.getScheduler().runTaskLaterAsynchronously(BukkitPlugin.getInstance(), runnable, ticks);
    }

    public void runTask(Runnable runnable) {
        Bukkit.getScheduler().runTask(BukkitPlugin.getInstance(), runnable);
    }

    public void runTaskLater(long ticks, Runnable runnable) {
        Bukkit.getScheduler().runTaskLater(BukkitPlugin.getInstance(), runnable, ticks);
    }

    public void callEvent(Event event) {
        Bukkit.getPluginManager().callEvent(event);
    }

    public void callEventAsync(Event event) {
        runTaskAsync(() -> Bukkit.getPluginManager().callEvent(event));
    }

    public void cancelTask(int taskID) {
        Bukkit.getScheduler().cancelTask(taskID);
    }
}
