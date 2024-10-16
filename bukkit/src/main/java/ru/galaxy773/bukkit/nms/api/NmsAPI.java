package ru.galaxy773.bukkit.nms.api;

import lombok.Getter;
import lombok.experimental.UtilityClass;
import ru.galaxy773.bukkit.nms.interfaces.NmsManager;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import ru.galaxy773.bukkit.api.utils.reflection.BukkitReflectionUtil;

@UtilityClass
public class NmsAPI {

    @Getter
    private NmsManager manager;

    public void init(JavaPlugin plugin) {
        if (manager != null) {
            return;
        }
        try {
            manager = (NmsManager) Class.forName("ru.galaxy773.bukkit.nms." + BukkitReflectionUtil.getServerVersion() + ".NmsManager_" + BukkitReflectionUtil.getServerVersion())
            		.getConstructor()
            		.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
            plugin.getServer().getConsoleSender().sendMessage("§4NmsManager for version " + BukkitReflectionUtil.getServerVersion() + " not found");
            Bukkit.getPluginManager().disablePlugin(plugin);
            Bukkit.setWhitelist(true);
        }
    }
}
