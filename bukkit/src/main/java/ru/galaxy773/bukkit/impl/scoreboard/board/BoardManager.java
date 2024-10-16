package ru.galaxy773.bukkit.impl.scoreboard.board;

import org.bukkit.entity.Player;
import ru.galaxy773.bukkit.api.scoreboard.Board;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class BoardManager {

    private final Map<String, CraftBoard> boards = new ConcurrentHashMap<>();
    
    void addBoard(Player player, CraftBoard board){
        String name = player.getName();
        Board check = boards.remove(name);
        if (check != null)
            check.remove();

        boards.put(name, board);
    }

    void removeBoard(String name) {
        boards.remove(name);
    }

    public Map<String, CraftBoard> getPlayersBoard() {
        return boards;
    }
}
