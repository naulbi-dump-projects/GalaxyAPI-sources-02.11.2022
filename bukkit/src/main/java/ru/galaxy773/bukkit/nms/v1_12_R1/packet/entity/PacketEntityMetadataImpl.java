package ru.galaxy773.bukkit.nms.v1_12_R1.packet.entity;

import net.minecraft.server.v1_12_R1.Entity;
import net.minecraft.server.v1_12_R1.PacketPlayOutEntityMetadata;
import ru.galaxy773.bukkit.nms.interfaces.entity.GEntity;
import ru.galaxy773.bukkit.nms.interfaces.packet.entity.PacketEntityMetadata;
import ru.galaxy773.bukkit.nms.v1_12_R1.entity.GEntityBase;

public class PacketEntityMetadataImpl extends GPacketEntityBase<PacketPlayOutEntityMetadata, GEntity>
        implements PacketEntityMetadata {

    public PacketEntityMetadataImpl(GEntity entity) {
        super(entity);
    }

    @Override
    protected PacketPlayOutEntityMetadata init() {
        Entity entity = ((GEntityBase<?>)this.entity).getEntityNms();
        return new PacketPlayOutEntityMetadata(entity.getId(), entity.getDataWatcher(), true);
    }
}
