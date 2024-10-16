package ru.galaxy773.bukkit.impl.scoreboard.board;

import lombok.RequiredArgsConstructor;
import ru.galaxy773.bukkit.api.scoreboard.ScoreBoardAPI;
import ru.galaxy773.bukkit.impl.scoreboard.board.lines.DisplayNameLine;

@RequiredArgsConstructor
public final class BoardTask implements Runnable {

    private int tick;
    private final BoardManager boardManager;
    private final ScoreBoardAPI scoreBoardAPI;
    
    @Override
    public void run() {
        try {
            tick = tick == Integer.MAX_VALUE ? 0 : tick;

            for (CraftBoard board : boardManager.getPlayersBoard().values()) {
                if (!board.isActive()) {
                    continue;
                }

                DisplayNameLine displayNameLine = board.getDisplayNameLine();
                long speed = displayNameLine.getSpeed();
                if (speed != -1 && displayNameLine.isAnimation()) {
                    if (tick != 0 && tick % speed != 0) {
                        continue;
                    }
                    displayNameLine.updateObjective().run();
                }

                for (Runnable runnable : board.getTasks().keySet()) {
                    speed = board.getTasks().get(runnable);
                    if (tick != 0 && tick % speed != 0) {
                        continue;
                    }

                    runnable.run();
                }
            }
            ++tick;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
