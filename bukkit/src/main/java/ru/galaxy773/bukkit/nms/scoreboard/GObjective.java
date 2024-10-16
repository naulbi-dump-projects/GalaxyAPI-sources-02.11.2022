package ru.galaxy773.bukkit.nms.scoreboard;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
public class GObjective {

    private final String name;
    @Setter
    private String displayName;
    private final ObjectiveType type;
}
