package ru.galaxy773.bukkit.nms.v1_12_R1.packet.world;

import net.minecraft.server.v1_12_R1.EnumParticle;
import net.minecraft.server.v1_12_R1.PacketPlayOutWorldParticles;
import org.bukkit.Location;
import ru.galaxy773.bukkit.api.effect.ParticleEffect;
import ru.galaxy773.bukkit.nms.interfaces.packet.world.PacketWorldParticles;
import ru.galaxy773.bukkit.nms.v1_12_R1.packet.GPacketBase;

public class PacketWorldParticlesImpl extends GPacketBase<PacketPlayOutWorldParticles> implements PacketWorldParticles {

    private ParticleEffect effect;
    private float centerX;
    private float centerY;
    private float centerZ;
    private float offsetX;
    private float offsetY;
    private float offsetZ;
    private float speed;
    private int amount;
    private boolean longDistance;
    private int[] data;

    public PacketWorldParticlesImpl(ParticleEffect effect, boolean longDistance, Location center,
                                       float offsetX, float offsetY, float offsetZ, float speed,
                                       int amount, int... data) {
        this.effect = effect;
        this.longDistance = longDistance;
        this.centerX = (float) center.getX();
        this.centerY = (float) center.getY();
        this.centerZ = (float) center.getZ();
        this.offsetX = offsetX;
        this.offsetY = offsetY;
        this.offsetZ = offsetZ;
        this.speed = speed;
        this.amount = amount;
        this.data = data;
    }

    @Override
    protected PacketPlayOutWorldParticles init() {
        return new PacketPlayOutWorldParticles(EnumParticle.a(effect.getId()), longDistance,
                centerX, centerY, centerZ, offsetX, offsetY, offsetZ, speed, amount, data);
    }

    @Override
    public void setData(int[] data) {
        this.data = data;
        init();
    }
}
