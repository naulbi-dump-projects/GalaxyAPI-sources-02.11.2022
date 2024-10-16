package ru.galaxy773.bukkit.api.utils.bukkit;

import lombok.experimental.UtilityClass;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.util.Vector;

import java.util.function.DoubleUnaryOperator;

@UtilityClass
public class LocationUtil {

    public Location stringToLocation(String loc, boolean pitchAndYaw) {
        String[] locSplit = loc.split(";");
        Location location = new Location(Bukkit.getWorld(locSplit[0]),
                Double.parseDouble(locSplit[1]),
                Double.parseDouble(locSplit[2]),
                Double.parseDouble(locSplit[3]));
        if (pitchAndYaw && locSplit.length >= 6) {
            location.setPitch(Float.parseFloat(locSplit[4]));
            location.setYaw(Float.parseFloat(locSplit[5]));
        }
        return location;
    }

    public String stringFromLocation(Location loc, boolean pitchAndYaw) {
        return loc.getWorld().getName() + ";" +
                loc.getX() + ";" +
                loc.getY() + ";" +
                loc.getZ() +
                (pitchAndYaw ? ";" + loc.getPitch() + ";" + loc.getYaw() : "");
    }

    public Location getRandomLocationInRadius(Location location, double xRadius, double zRadius) {
        DoubleUnaryOperator randomizer = d -> d * (2.0 * Math.random() - 1.0);
        Location loc = location.clone().add(randomizer.applyAsDouble(xRadius), 0.0, randomizer.applyAsDouble(zRadius));
        loc.setY(loc.getWorld().getHighestBlockYAt(loc));
        return loc;
    }

    public Location getRandomLocationInRadius(Location location, double xzRadius) {
        return getRandomLocationInRadius(location, xzRadius);
    }

    public double distance(Location first, Location second) {
        if (first.getWorld().getName().equals(second.getWorld().getName())) {
            return first.distanceSquared(second);
        }
        return -1;
    }

    public Location faceEntity(Location location, Entity entity) {
        Vector direction = location.toVector().subtract(entity.getLocation().toVector());
        direction.multiply(-1);
        location.setDirection(direction);
        return location;
    }

    public byte getFixRotation(float yawPitch) {
        return (byte) (yawPitch * 256.0F / 360.0F);
    }

    public long convert(Location loc) {
        return (((loc.getBlockX() & 0x3FFFFFF) << 6) | ((loc.getBlockY() & 0xFFF) << 26) | (loc.getBlockZ() & 0x3FFFFFF)) + loc.getBlockX();
    }
}
