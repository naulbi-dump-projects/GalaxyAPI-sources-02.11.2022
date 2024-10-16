package ru.galaxy773.multiplatform.api.game;

import lombok.Getter;

public enum GameState {

    WAITING,
    STARTING,
    GAME,
    END,
    RESTART;

    @Getter
    private static GameState current = WAITING;
    @Getter
    private static long gameTime = 0L;

    public static void setCurrent(GameState current) {
        GameState.current = current;
        switch (current) {
            case GAME: {
                GameState.gameTime = System.currentTimeMillis();
                break;
            }
            case END: {
                GameState.gameTime = 0L;
                break;
            }
        }
    }
}
