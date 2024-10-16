package ru.galaxy773.bukkit.nms.v1_16_R3;

import net.minecraft.server.v1_16_R3.WorldBorder;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.craftbukkit.v1_16_R3.CraftWorld;
import ru.galaxy773.bukkit.nms.interfaces.GWorldBorder;

public class GWorldBorderImpl extends WorldBorder implements GWorldBorder {

    private World world;

    public GWorldBorderImpl(World world) {
        setWorld(world);
    }

    @Override
    public World getWorld() {
        return world;
    }

    @Override
    public void setWorld(World world) {
        this.world = world;
        super.world = ((CraftWorld)world).getHandle();
    }

    @Override
    public void setRadius(double size) {
        super.setSize(size);
    }

    @Override
    public double getRadius() {
        return super.getSize();
    }

    @Override
    public void setCenter(Location center) {
        setCenter(center.getX(), center.getZ());
    }

    @Override
    public int getPortalTeleportBoundary() {
        return super.m();
    }

    @Override
    public long getSpeed() {
        return super.j();
    }

    @Override
    public double getOldRadius() {
        return super.j();
    }

    public WorldBorder get() {
        return this;
    }
}
