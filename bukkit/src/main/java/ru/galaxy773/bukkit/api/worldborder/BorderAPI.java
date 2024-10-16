package ru.galaxy773.bukkit.api.worldborder;

import org.bukkit.Location;
import org.bukkit.entity.Player;

public interface BorderAPI {

    void sendRedScreen(Player player);

    void sendRedScreen(Player player, long time);

    void sendRedScreen(Player player, long time, int percentage);

    void sendBorder(Player player, Location location, double size);

    void removeBorder(Player player);
}
