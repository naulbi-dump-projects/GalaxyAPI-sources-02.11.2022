package ru.galaxy773.bukkit.nms.scoreboard;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum TeamAction {
    CREATE(0),
    REMOVE(1),
    UPDATE(2),
    PLAYERS_ADD(3),
    PLAYERS_REMOVE(4);

    private final int mode;
}
