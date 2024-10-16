package ru.galaxy773.bukkit.api.hologram.lines;

import java.util.function.Supplier;

public interface AnimationHoloLine {

    void setReplacerLine(Supplier<String> supplier);

    int getSpeed();

    void setSpeed(int speed);
}
