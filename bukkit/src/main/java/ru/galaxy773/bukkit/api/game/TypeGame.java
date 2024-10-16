package ru.galaxy773.bukkit.api.game;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

@AllArgsConstructor
@Getter
public enum TypeGame {

    SOLO("Solo"),
    DOUBLES("Doubles"),
    TEAM("Team");

    private final String name;

    public static TypeGame tryGet(String typeGame) {
        return Arrays.stream(values())
                .filter(typeGame1 -> typeGame1.name.equalsIgnoreCase(typeGame))
                .findFirst()
                .orElse(null);
    }
}
