package ru.galaxy773.menu.manager;

import org.bukkit.event.EventPriority;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import ru.galaxy773.bukkit.api.gamer.GamerManager;
import ru.galaxy773.bukkit.api.utils.listener.FastEvent;
import ru.galaxy773.bukkit.api.utils.listener.FastListener;
import ru.galaxy773.menu.MenuPlugin;
import ru.galaxy773.menu.api.menu.Menu;
import ru.galaxy773.menu.api.menu.manager.MenuManager;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MenuManagerImpl implements MenuManager {

    private final Map<List<String>, Menu> menu = new HashMap<>();

    public MenuManagerImpl(MenuPlugin javaPlugin) {
        FastListener.create().event(FastEvent.builder(PlayerCommandPreprocessEvent.class)
                .priority(EventPriority.LOWEST)
                .ignoreCancelled(true)
                .filter(event -> !event.getMessage().contains(" "))
                .handler(event -> {
                    Menu menu = this.getMenu(event.getMessage().substring(1).toLowerCase());
                    if (menu != null) {
                        menu.open(GamerManager.getGamer(event.getPlayer()));
                        event.setCancelled(true);
                    }
        }).build()).register(javaPlugin);
    }

    @Override
    public Map<List<String>, Menu> getMenu() {
        return this.menu;
    }

    @Override
    public Menu getMenu(String name) {
        for (List<String> commands : this.menu.keySet()) {
            if (!commands.contains(name))
                continue;

            return this.menu.get(commands);
        }
        return null;
    }

    @Override
    public void addMenu(List<String> commands, Menu menu) {
        this.menu.put(commands, menu);
    }
}

