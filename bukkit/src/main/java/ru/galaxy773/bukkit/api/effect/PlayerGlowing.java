package ru.galaxy773.bukkit.api.effect;

import org.bukkit.entity.Player;

import java.util.List;

public interface PlayerGlowing {

    Player getOwner();

    void addEntity(Player player);
    void addEntity(Player... players);
    void addEntity(List<Player> players);

    void removeEntity(Player player);
    void removeEntity(Player... players);
    void removeEntity(List<Player> players);

    List<String> getPlayers();

    void remove();
}
