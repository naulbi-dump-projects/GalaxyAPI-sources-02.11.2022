package ru.galaxy773.bukkit.nms.v1_12_R1.packet.entity;

import net.minecraft.server.v1_12_R1.PacketPlayOutEntity;
import ru.galaxy773.bukkit.nms.interfaces.entity.GEntity;
import ru.galaxy773.bukkit.nms.interfaces.packet.entity.PacketEntityLook;

public class PacketEntityLookImpl extends GPacketEntityBase<PacketPlayOutEntity.PacketPlayOutEntityLook, GEntity>
        implements PacketEntityLook {

    private byte pitch;
    private byte yaw;

    public PacketEntityLookImpl(GEntity entity, byte yaw, byte pitch) {
        super(entity);
        this.yaw = yaw;
        this.pitch = pitch;
    }

    @Override
    public void setPitch(byte pitch) {
        this.pitch = pitch;
        init();
    }

    @Override
    public void setYaw(byte yaw) {
        this.yaw = yaw;
        init();
    }

    @Override
    protected PacketPlayOutEntity.PacketPlayOutEntityLook init() {
        return new PacketPlayOutEntity.PacketPlayOutEntityLook(entity.getEntityID(), yaw, pitch, true);
    }
}
