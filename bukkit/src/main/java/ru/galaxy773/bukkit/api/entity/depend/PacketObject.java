package ru.galaxy773.bukkit.api.entity.depend;

import org.bukkit.entity.Player;

import java.util.Collection;

public interface PacketObject {
    
    boolean isVisibleTo(Player player);
    Collection<String> getVisiblePlayers();

    void showTo(Player player);
    void removeTo(Player player);

    void hideAll();

    Player getOwner();

    void setOwner(Player owner);

    boolean isPublic();
    void setPublic(boolean vision);

    void remove();
}
