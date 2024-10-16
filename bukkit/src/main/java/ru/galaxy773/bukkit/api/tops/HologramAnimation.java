package ru.galaxy773.bukkit.api.tops;

import ru.galaxy773.multiplatform.api.utils.time.TimeUtil;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.function.Supplier;

public final class HologramAnimation implements Supplier<String> {

    private final int timeMinutes;

    private LocalDateTime nextTime;

    public HologramAnimation(int timeMinutes) {
        this.timeMinutes = timeMinutes;
        nextTime = LocalDateTime.now().plusMinutes(timeMinutes);
    }
    
    @Override
    public String get() {
        Duration between = Duration.between(LocalDateTime.now(), nextTime);
        if (between.isNegative() || between.isZero()) {
            nextTime = LocalDateTime.now().plusMinutes(timeMinutes);
            return "\u00A7a\u00A7l\u041E\u0431\u043D\u043E\u0432\u043B\u0435\u043D\u043E";
        }
        return "\u00A7f\u0414\u043E \u043E\u0431\u043D\u043E\u0432\u043B\u0435\u043D\u0438\u044F: \u00A7c" + TimeUtil.getTimeLeft(between.getSeconds());
    }
}
