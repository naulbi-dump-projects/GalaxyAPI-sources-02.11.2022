package ru.galaxy773.multiplatform.api.gamer.data;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum CustomizationType {

    TITUL("titul"),
    JOIN_MESSAGE("join_message"),
    QUIT_MESSAGE("quit_message"),
    PREFIX_COLOR("prefix_color");

    private final String columnName;
}
