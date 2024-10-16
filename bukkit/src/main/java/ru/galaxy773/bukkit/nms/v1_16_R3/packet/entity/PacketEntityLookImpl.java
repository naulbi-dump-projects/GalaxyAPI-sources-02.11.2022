package ru.galaxy773.bukkit.nms.v1_16_R3.packet.entity;

import net.minecraft.server.v1_16_R3.PacketPlayOutEntity;
import ru.galaxy773.bukkit.nms.interfaces.entity.GEntity;
import ru.galaxy773.bukkit.nms.interfaces.packet.entity.PacketEntityLook;

public class PacketEntityLookImpl extends GPacketEntityBase<PacketPlayOutEntity.PacketPlayOutEntityLook, GEntity>
        implements PacketEntityLook {

    private byte pitch;
    private byte yaw;
    
    public PacketEntityLookImpl(final GEntity entity, final byte yaw, final byte pitch) {
        super(entity);
        this.yaw = yaw;
        this.pitch = pitch;
    }
    
    @Override
    public void setPitch(final byte pitch) {
        this.pitch = pitch;
        init();
    }
    
    @Override
    public void setYaw(final byte yaw) {
        this.yaw = yaw;
        init();
    }
    
    protected PacketPlayOutEntity.PacketPlayOutEntityLook init() {
        return new PacketPlayOutEntity.PacketPlayOutEntityLook(this.entity.getEntityID(), yaw, pitch, true);
    }
}
