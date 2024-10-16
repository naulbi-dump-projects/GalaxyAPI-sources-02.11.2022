package ru.galaxy773.bukkit.api.gui;

import org.bukkit.entity.Player;

import java.util.Map;

public interface GuiManager<G> {

    void createGui(Class<? extends G> clazz);

    void removeGui(Class<? extends G> clazz);

    <T extends G> T getGui(Class<T> clazz, Player player);

    void removeALL(Player player);

    Map<String, Map<String, G>> getPlayerGuis();
}

