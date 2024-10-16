package ru.galaxy773.boxes.utils;

import com.google.common.collect.ImmutableList;
import lombok.experimental.UtilityClass;
import org.bukkit.Color;
import org.bukkit.FireworkEffect;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Firework;
import org.bukkit.inventory.meta.FireworkMeta;
import ru.galaxy773.boxes.api.BoxReward;
import ru.galaxy773.bukkit.api.utils.reflection.BukkitReflectionUtil;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@UtilityClass
public final class BoxUtil {
    
    private final Random RANDOM = new Random();
    private final ImmutableList<Color> COLORS = ImmutableList.of(Color.RED, Color.AQUA, Color.GREEN, Color.ORANGE, Color.LIME, Color.BLUE, Color.MAROON, Color.WHITE);

    public BoxReward getWinItem(List<BoxReward> rewards) {
        int totalChance = 0;
        for (BoxReward reward : rewards)
            totalChance += reward.getChance();

        int x = RANDOM.nextInt(Math.round(totalChance)) + 1;
        double l = 0.0;
        for (BoxReward reward : rewards) {
            if (x <= l + reward.getChance())
                return reward;

            l += reward.getChance();
        }

        return null;
    }

    public List<Location> getCircleSide(Location center, double radius) {
        World world = center.getWorld();
        double increment = Math.PI / 50;
        ArrayList<Location> locations = new ArrayList<Location>();
        for (int i = 0; i < 100; ++i) {
            double angle = (double)i * increment;
            double x = center.getX() + radius * Math.cos(angle);
            double z = center.getZ() + radius * Math.sin(angle);
            locations.add(new Location(world, x, center.getY(), z, center.getYaw(), center.getPitch()));
        }
        return locations;
    }

    public void launchFirework(Location location) {
        Firework firework = location.getWorld().spawn(location, Firework.class);
        FireworkMeta meta = firework.getFireworkMeta();
        meta.addEffect(FireworkEffect.builder().flicker(false).with(FireworkEffect.Type.BALL).trail(false).withColor(new Color[]{COLORS.get(RANDOM.nextInt(COLORS.size())), COLORS.get(RANDOM.nextInt(COLORS.size())), COLORS.get(RANDOM.nextInt(COLORS.size()))}).build());
        firework.setFireworkMeta(meta);
        try {
            Class<?> craftFirework = null;
            try {
                craftFirework = BukkitReflectionUtil.getCraftBukkitClass("entity.CraftFirework");
            }
            catch (Exception e) {
                e.printStackTrace();
            }
            Object handle = craftFirework.getMethod("getHandle", new Class[0]).invoke(firework);
            handle.getClass().getField("expectedLifespan").setInt(handle, 1);
        }
        catch (IllegalAccessException | IllegalArgumentException | NoSuchFieldException | NoSuchMethodException | SecurityException | InvocationTargetException e) {
            e.printStackTrace();
        }
    }
}

