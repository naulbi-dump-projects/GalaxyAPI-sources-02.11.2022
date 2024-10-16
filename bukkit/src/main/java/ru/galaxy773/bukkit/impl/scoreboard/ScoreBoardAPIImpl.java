package ru.galaxy773.bukkit.impl.scoreboard;

import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.ScoreboardManager;
import ru.galaxy773.bukkit.api.BukkitPlugin;
import ru.galaxy773.bukkit.api.scoreboard.*;
import ru.galaxy773.bukkit.impl.scoreboard.board.BoardListener;
import ru.galaxy773.bukkit.impl.scoreboard.board.BoardManager;
import ru.galaxy773.bukkit.impl.scoreboard.board.BoardTask;
import ru.galaxy773.bukkit.impl.scoreboard.board.CraftBoard;
import ru.galaxy773.bukkit.impl.scoreboard.objective.CraftObjective;
import ru.galaxy773.bukkit.impl.scoreboard.objective.ObjectiveListener;
import ru.galaxy773.bukkit.impl.scoreboard.objective.ObjectiveManager;
import ru.galaxy773.bukkit.impl.scoreboard.tag.CraftTag;
import ru.galaxy773.bukkit.nms.scoreboard.ObjectiveType;

import java.util.Collections;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ScoreBoardAPIImpl implements ScoreBoardAPI {

    private Objective tabObjective;
    private org.bukkit.scoreboard.Objective healthObjective;
    @Getter
    private final Map<String, PlayerTag> playerTags = new ConcurrentHashMap<>();
    @Getter
    private final BoardManager boardManager;
    @Getter
    private final ObjectiveManager objectiveManager;

    public ScoreBoardAPIImpl() {
        boardManager = new BoardManager();
        objectiveManager = new ObjectiveManager();
        new BoardListener(this);
        Bukkit.getScheduler().runTaskTimerAsynchronously(BukkitPlugin.getInstance(), new BoardTask(boardManager, this), 0, 1L);
        new ObjectiveListener(this);
    }

    @Override
    public Board createBoard() {
        return new CraftBoard(boardManager);
    }

    @Override
    public Objective createObjective(String name, String value) {
        return new CraftObjective(objectiveManager, name, value, ObjectiveType.INTEGER);
    }

    @Override
    public PlayerTag createTag(String name) {
        return new CraftTag(name);
    }

    @Override
    public void createGameObjective(boolean health, boolean tab) {
        if (health) {
            if (healthObjective != null)
                healthObjective.unregister();
            ScoreboardManager scoreboardManager = Bukkit.getScoreboardManager();
            healthObjective = scoreboardManager.getMainScoreboard().registerNewObjective("showhealth", "health");
            healthObjective.setDisplaySlot(org.bukkit.scoreboard.DisplaySlot.BELOW_NAME);
            healthObjective.setDisplayName("§4❤");
            Bukkit.getOnlinePlayers().forEach(player -> healthObjective
                    .getScore(player.getName())
                    .setScore((int) player.getHealth()));
        }
        if (tab) {
            if (tabObjective != null)
                tabObjective.remove();
            tabObjective = createObjective("playerlist", "stat");
            tabObjective.setDisplaySlot(DisplaySlot.LIST);
            tabObjective.setPublic(true);
        }
    }

    @Override
    public void setScoreTab(Player player, int score) {
        if (player == null || tabObjective == null) {
            return;
        }
        tabObjective.setScore(player, score);
    }

    @Override
    public void unregisterObjectives() {
        if (tabObjective != null) {
            tabObjective.remove();
            tabObjective = null;
        }
        if (healthObjective != null) {
            healthObjective.unregister();
            healthObjective = null;
        }
    }

    @Override
    public Map<String, PlayerTag> getActiveDefaultTag() {
        return Collections.unmodifiableMap(playerTags);
    }

    @Override
    public void setDefaultTag(Player player, PlayerTag playerTag) {
        playerTags.values().forEach(tag -> tag.sendTo(player));
        playerTags.put(player.getName(), playerTag);
        playerTag.sendToAll();
    }

    @Override
    public void removeDefaultTag(Player player) {
        PlayerTag playerTag = playerTags.remove(player.getName());
        if (playerTag == null) {
            return;
        }
        playerTag.remove();
    }

    @Override
    public void removeDefaultTags() {
        playerTags.values().forEach(PlayerTag::remove);
        playerTags.clear();
    }

    @Override
    public void setPrefix(Player player, String prefix) {
        PlayerTag playerTag = playerTags.get(player.getName());
        if (playerTag == null) {
            return;
        }
        playerTag.setPrefix(prefix);
        playerTag.sendToAll();
    }

    @Override
    public void setSuffix(Player player, String suffix) {
        PlayerTag playerTag = playerTags.get(player.getName());
        if (playerTag == null) {
            return;
        }
        playerTag.setSuffix(suffix);
        playerTag.sendToAll();
    }

    @Override
    public void removeBoard(Player player) {
        Board board = getBoard(player);
        if (board == null) {
            return;
        }
        board.remove();
    }

    @Override
    public Board getBoard(Player player) {
        return boardManager.getPlayersBoard().get(player.getName());
    }

    @Override
    public PlayerTag getPlayerTag(Player player) {
        return this.playerTags.get(player.getName());
    }

    @Override
    public Map<String, Board> getActiveBoards() {
        return Collections.unmodifiableMap(boardManager.getPlayersBoard());
    }
}
