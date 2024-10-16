package ru.galaxy773.menu.api.menu.manager;

import ru.galaxy773.menu.api.menu.Menu;

import java.util.List;
import java.util.Map;

public interface MenuManager {

    Map<List<String>, Menu> getMenu();

    Menu getMenu(String var1);

    void addMenu(List<String> var1, Menu var2);
}

