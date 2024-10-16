package ru.galaxy773.bukkit.nms.v1_12_R1;

import net.minecraft.server.v1_12_R1.WorldBorder;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.craftbukkit.v1_12_R1.CraftWorld;
import ru.galaxy773.bukkit.nms.interfaces.GWorldBorder;

public class GWorldBorderImpl extends WorldBorder implements GWorldBorder {

    private World world;

    GWorldBorderImpl(World world) {
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
        return super.l();
    }

    @Override
    public long getSpeed() {
        return super.i();
    }

    @Override
    public double getOldRadius() {
        return super.j();
    }

    public WorldBorder get() {
        return this;
    }
}
