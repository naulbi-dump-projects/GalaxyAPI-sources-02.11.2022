package ru.galaxy773.bukkit.api.scoreboard;

import org.bukkit.entity.Player;

import java.util.Collection;
import java.util.List;

public interface PlayerTag {

    void sendToAll();
    void sendTo(Collection<Player> players);
    void sendTo(Player player);

    void addPlayerToTeam(Player player);
    void addPlayersToTeam(Collection<Player> players);
    void addNamesToTeam(Collection<String> names);

    void removePlayerFromTeam(Player player);

    boolean isContainsTeam(Player player);
    boolean isEmpty();
   
    List<String> getPlayersTeam();
    
    void setFriendInv(boolean friendInv);

    void setPrefixSuffix(String prefix, String suffix);
    void setPrefix(String prefix);
    void setSuffix(String suffix);
    void disableCollidesForAll();
    void setCollides(Collides collides);

    void remove();
}
