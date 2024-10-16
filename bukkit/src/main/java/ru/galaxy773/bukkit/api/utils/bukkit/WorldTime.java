package ru.galaxy773.bukkit.api.utils.bukkit;

import com.google.common.base.Preconditions;
import lombok.Builder;
import lombok.Getter;
import lombok.experimental.UtilityClass;
import org.bukkit.World;

import java.util.HashSet;
import java.util.Set;

@UtilityClass
public class WorldTime {

    public WorldTimeData.WorldTimeDataBuilder getTimeBuilder() {
        return WorldTimeData.builder();
    }

    public void freeze(WorldTimeData worldTimeData) {
        TimeTask.WORLD_TIME_DATA.add(worldTimeData);
    }

    public boolean isFreezed(String worldName) {
        return TimeTask.WORLD_TIME_DATA.stream().anyMatch(worldTimeData -> worldTimeData.getWorld().getName() == worldName);
    }

    public WorldTimeData getWorldTimeData(String worldName) {
        return TimeTask.WORLD_TIME_DATA.stream().filter(worldTimeData -> worldTimeData.getWorld().getName() == worldName).findFirst().orElse(null);
    }

    public void unfreeze(String worldName) {
        TimeTask.WORLD_TIME_DATA.removeIf(worldTimeData -> worldTimeData.getWorld().getName() == worldName);
    }

    @Getter
    private static class WorldTimeData {

        private final World world;
        private long time = -1;
        private final boolean storm;

        @Builder(buildMethodName = "build")
        public WorldTimeData(World world, long time, boolean storm) {
            Preconditions.checkArgument(world != null, "World can't be null");
            Preconditions.checkArgument(time > 0, "Time is negative");
            this.world = world;
            this.time = time;
            this.storm = storm;
        }
    }

    public static class TimeTask implements Runnable {

        private static final Set<WorldTimeData> WORLD_TIME_DATA = new HashSet<>();

        @Override
        public void run() {
            for (WorldTimeData worldTimeData : WORLD_TIME_DATA) {
                World world = worldTimeData.getWorld();
                if (world == null) {
                    continue;
                }
                world.setTime(worldTimeData.getTime());
                world.setStorm(worldTimeData.isStorm());
                world.setThundering(worldTimeData.isStorm());
            }
        }
    }
}
