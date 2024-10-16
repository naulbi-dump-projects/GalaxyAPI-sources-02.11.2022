package ru.galaxy773.bukkit.impl.scoreboard.board.lines;

import ru.galaxy773.bukkit.api.scoreboard.Board;
import ru.galaxy773.bukkit.api.scoreboard.BoardLine;
import ru.galaxy773.bukkit.impl.scoreboard.board.CraftBoard;
import ru.galaxy773.bukkit.nms.scoreboard.GTeam;

public class CraftBoardLine implements BoardLine {

    private final CraftBoard board;

    private int number;
    private GTeam team;
    private final String text;
    private final boolean dynamic;

    public CraftBoardLine(CraftBoard board, int number, String text, boolean dynamic) {
        this.board = board;
        this.number = number;
        this.text = text;
        this.dynamic = dynamic;
    }

    public CraftBoardLine(CraftBoard board, int number, String text, boolean dynamic, GTeam team) {
        this(board, number, text, dynamic);
        this.team = team;
    }

    public GTeam getTeam() {
        return team;
    }

    public String getText() {
        return text;
    }

    public void setTeam(GTeam team) {
        this.team = team;
    }

    @Override
    public Board getBoard() {
        if (board.isActive()) {
            return board;
        }

        return null;
    }

    @Override
    public int getNumber() {
        return number;
    }

    @Override
    public void setNumber(int number) {
        if (board == null)
            return;

        this.number = number;
        board.lines.put(number, this);

        if (isDynamic()) {
            String pref = team.getPrefix();
            String name = team.getPlayers().get(0);
            String suf = team.getSuffix();

            board.createDynamicLine(number, pref, name, suf);
            return;
        }

        board.sendLine(this, number, team != null);
    }

    @Override
    public void remove() {
        if (board == null)
            return;
        board.removeLine(number);
    }

    @Override
    public boolean isDynamic() {
        return dynamic;
    }
}
