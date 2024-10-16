package ru.galaxy773.multiplatform.api.gamer.data;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum NetworkingType {

    EXP("exp"),
    COINS("coins"),
    GOLD("gold"),
    PLAY_TIME("play_time"),
    LEVEL_REWARD("level_reward");

    private final String columnName;
}
