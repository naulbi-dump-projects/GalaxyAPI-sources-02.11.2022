package ru.galaxy773.bukkit.nms.v1_12_R1.packet.entity;

import net.minecraft.server.v1_12_R1.PacketPlayOutMount;
import ru.galaxy773.bukkit.nms.interfaces.entity.GEntity;
import ru.galaxy773.bukkit.nms.interfaces.packet.entity.PacketMount;
import ru.galaxy773.bukkit.nms.v1_12_R1.entity.GEntityBase;

public class PacketMountImpl extends GPacketEntityBase<PacketPlayOutMount, GEntity> implements PacketMount {

    public PacketMountImpl(GEntity entity) {
        super(entity);
    }

    @Override
    protected PacketPlayOutMount init() {
        return new PacketPlayOutMount(((GEntityBase<?>)entity).getEntityNms());
    }
}
