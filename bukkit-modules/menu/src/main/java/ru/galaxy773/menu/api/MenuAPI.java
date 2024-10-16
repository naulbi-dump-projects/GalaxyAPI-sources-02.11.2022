package ru.galaxy773.menu.api;

import lombok.experimental.UtilityClass;
import ru.galaxy773.menu.MenuPlugin;
import ru.galaxy773.menu.api.menu.manager.MenuManager;
import ru.galaxy773.menu.manager.MenuManagerImpl;

@UtilityClass
public final class MenuAPI {

    private final MenuManager MENU_MANAGER = new MenuManagerImpl(MenuPlugin.getInstance());

    public MenuManager getMenuManager() {
        return MENU_MANAGER;
    }
}

