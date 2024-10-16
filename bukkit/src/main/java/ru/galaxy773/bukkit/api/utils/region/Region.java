package ru.galaxy773.bukkit.api.utils.region;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;

@AllArgsConstructor
public class Region  {

    @Getter
    private final String world;
    private final int x1;
    private final int y1;
    private final int z1;
    private final int x2;
    private final int y2;
    private final int z2;

    public Region(String world, Location min, Location max) {
        this(world, min.getBlockX(), min.getBlockY(), min.getBlockZ(), max.getBlockX(), max.getBlockY(), max.getBlockZ());
    }

    public boolean isInRegion(Location loc) {
        return RegionUtil.isIn(loc.getWorld().getName(), world, this.x1, this.y1, this.z1, this.x2, this.y2, this.z2, loc.getBlockX(), loc.getBlockY(), loc.getBlockZ());
    }

    public boolean isInRegionWithAdditionRadius(Location loc, int radius) {
        return RegionUtil.isIn(loc.getWorld().getName(), world, this.x1 - radius, this.y1 - radius, this.z1 - radius, this.x2 + radius, this.y2 + radius, this.z2 + radius, loc.getBlockX(), loc.getBlockY(), loc.getBlockZ());
    }

    public Block getAnyBlock() {
        World world = Bukkit.getWorld(this.world);
        for (int x = this.x1; x <= this.x2; ++x) {
            for (int y = this.y2; y >= this.y1; --y) {
                for (int z = this.z1; z <= this.z2; ++z) {
                    Location loc = new Location(world, x, y, z);
                    if (loc.getBlock().getType() != Material.AIR) {
                        return loc.getBlock();
                    }
                }
            }
        }
        return null;
    }
}