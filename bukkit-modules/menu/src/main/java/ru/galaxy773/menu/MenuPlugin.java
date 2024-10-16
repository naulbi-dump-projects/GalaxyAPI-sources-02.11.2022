package ru.galaxy773.menu;

import lombok.Getter;
import org.bukkit.plugin.java.JavaPlugin;
import ru.galaxy773.bukkit.api.utils.bukkit.BukkitUtil;
import ru.galaxy773.menu.api.MenuAPI;
import ru.galaxy773.menu.api.menu.manager.MenuManager;
import ru.galaxy773.menu.manager.MenuLoader;

import java.util.logging.Level;

public class MenuPlugin extends JavaPlugin {

    @Getter
    private static MenuPlugin instance;

    public void onLoad() {
        instance = this;
    }

    public void onEnable() {
        MenuManager menuManager = MenuAPI.getMenuManager();
        BukkitUtil.runTaskAsync(() -> {
            MenuLoader.loadMenu(this).forEach(menuManager::addMenu);
            getLogger().log(Level.INFO, menuManager.getMenu().size() + " menu loaded");
        });
    }
}

