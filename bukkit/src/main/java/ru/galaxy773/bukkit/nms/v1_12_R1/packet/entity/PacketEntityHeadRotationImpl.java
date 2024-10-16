package ru.galaxy773.bukkit.nms.v1_12_R1.packet.entity;

import net.minecraft.server.v1_12_R1.PacketPlayOutEntityHeadRotation;
import ru.galaxy773.bukkit.nms.interfaces.entity.GEntity;
import ru.galaxy773.bukkit.nms.interfaces.packet.entity.PacketEntityHeadRotation;
import ru.galaxy773.bukkit.nms.v1_12_R1.entity.GEntityBase;

public class PacketEntityHeadRotationImpl extends GPacketEntityBase<PacketPlayOutEntityHeadRotation, GEntity>
        implements PacketEntityHeadRotation {

    private byte yaw;

    public PacketEntityHeadRotationImpl(GEntity entity, byte yaw) {
        super(entity);
        this.yaw = yaw;
    }

    @Override
    public void setYaw(byte yaw) {
        this.yaw = yaw;
        init();
    }

    @Override
    protected PacketPlayOutEntityHeadRotation init() {
        return new PacketPlayOutEntityHeadRotation(((GEntityBase<?>)entity).getEntityNms(), yaw);
    }
}
