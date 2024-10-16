package ru.galaxy773.bukkit.api.core;

import org.bukkit.entity.Player;
import ru.galaxy773.bukkit.api.gamer.BukkitGamer;

public interface CoreAPI {

    String getServerName();

    String getConfigDirectory();
    String getServerDirectory();

    String getGameWorld();

    void registerGame(String mapName, boolean alwaysDay);

    default void registerGame(String mapName) {
        registerGame(mapName, true);
    }

    void registerLobby(int time);

    void restart();

    void sendToServer(Player player, String regex);
    void sendToServer(BukkitGamer gamer, String regex);

    void executeCommand(Player player, String command);
    void executeCommand(BukkitGamer gamer, String command);

    boolean isRestart();
    void setRestart(boolean restart);

    int getCoreOnline();
    int getOnline(String regex);

    
}
