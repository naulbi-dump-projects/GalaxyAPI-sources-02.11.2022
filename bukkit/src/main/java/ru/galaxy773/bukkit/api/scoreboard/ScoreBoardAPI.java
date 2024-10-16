package ru.galaxy773.bukkit.api.scoreboard;

import org.bukkit.entity.Player;

import java.util.Map;

public interface ScoreBoardAPI {

    Board createBoard();

    Objective createObjective(String name, String value);

    PlayerTag createTag(String name);

    void createGameObjective(boolean health, boolean tab);

    void setScoreTab(Player player, int score);

    void unregisterObjectives();

    Map<String, PlayerTag> getActiveDefaultTag();

    void setDefaultTag(Player player, PlayerTag playerTag);
    void removeDefaultTag(Player player);
    void removeDefaultTags();
    void setPrefix(Player player, String prefix);
    void setSuffix(Player player, String suffix);

    void removeBoard(Player player);

    Board getBoard(Player player);

    PlayerTag getPlayerTag(Player player);

    Map<String, Board> getActiveBoards();

    Map<String, PlayerTag> getPlayerTags();
}
