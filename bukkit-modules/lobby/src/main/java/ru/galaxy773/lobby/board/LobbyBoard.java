package ru.galaxy773.lobby.board;

import org.bukkit.entity.Player;
import ru.galaxy773.bukkit.api.BukkitAPI;
import ru.galaxy773.bukkit.api.gamer.BukkitGamer;
import ru.galaxy773.bukkit.api.gamer.GamerManager;
import ru.galaxy773.bukkit.api.scoreboard.Board;
import ru.galaxy773.bukkit.api.scoreboard.ScoreBoardAPI;
import ru.galaxy773.multiplatform.api.locale.Lang;

public abstract class LobbyBoard {

    protected static final ScoreBoardAPI SCORE_BOARD_API = BukkitAPI.getScoreBoardAPI();
    protected final Player owner;
    protected final BukkitGamer gamer;
    protected final Board board;

    public LobbyBoard(Player owner) {
        this.owner = owner;
        this.gamer = GamerManager.getGamer(owner);
        this.board = SCORE_BOARD_API.createBoard();
        this.board.setDisplayName(Lang.getMessage("LOBBY_BOARD_NAME"));
    }

    public void show() {
        this.board.showTo(this.owner);
    }

    public void remove() {
        this.board.remove();
    }
}

