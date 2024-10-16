package ru.galaxy773.multiplatform.api.utils.cooldown;

import lombok.experimental.UtilityClass;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@UtilityClass
public class Cooldown {

    private static final ScheduledExecutorService EXECUTOR_SERVICE = Executors.newSingleThreadScheduledExecutor();
    private static final Map<String, Map<String, Long>> COOLDOWNS = new ConcurrentHashMap<>();

    public void addCooldown(String key, long ticks) {
        addCooldown(key, "global", ticks);
    }

    public boolean hasCooldown(String key) {
        return hasCooldown(key, "global");
    }

    public boolean hasCooldown(String key, String type) {
        if (key == null || type == null) {
            return false;
        }

        return COOLDOWNS.containsKey(key) && COOLDOWNS.get(key).containsKey(type.toLowerCase());
    }

    public int getCooldownLeft(String key, String type) {
        if (!hasCooldown(key, type)) {
            return 0;
        }
        Map<String, Long> cooldownData = COOLDOWNS.get(key);
        if (cooldownData == null) {
            return 0;
        }
        Long startTime = cooldownData.get(type.toLowerCase());
        if (startTime == null) {
            return 0;
        }
        int time = (int) ((startTime - System.currentTimeMillis()) / 50 / 20);
        return (time == 0 ? 1 : time);
    }
    
    public void addCooldown(String key, String type, long ticks) {
        long time = System.currentTimeMillis() + ticks * 50;

        Map<String, Long> cooldownData = COOLDOWNS.get(key);
        if (cooldownData == null) {
            cooldownData = new ConcurrentHashMap<>();
            cooldownData.put(type.toLowerCase(), time);
            COOLDOWNS.put(key, cooldownData);
            return;
        }

        if (cooldownData.containsKey(type.toLowerCase()))
            return;

        cooldownData.put(type.toLowerCase(), time);
    }
    
    public boolean hasOrAddCooldown(String key, String type, long tick) {
        if (!hasCooldown(key, type)) {
            addCooldown(key, type, tick);
            return false;
        }

        return true;
    }

    public boolean removeCooldown(String key, String type) {
        return COOLDOWNS.containsKey(key) && COOLDOWNS.get(key).remove(type) != null;
    }

    static {
        EXECUTOR_SERVICE.scheduleAtFixedRate(() -> COOLDOWNS.forEach((key, data) -> {
            data.forEach((type, time) -> {
                if (time < System.currentTimeMillis()) {
                    data.remove(type);
                }
            });
            if (data.isEmpty()) {
                COOLDOWNS.remove(key);
            }
        }), 0, 50, TimeUnit.MILLISECONDS);
    }
}
