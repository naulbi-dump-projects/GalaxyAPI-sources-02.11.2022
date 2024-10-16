package ru.galaxy773.bukkit.nms.v1_16_R3.packet.entity;

import net.minecraft.server.v1_16_R3.Entity;
import net.minecraft.server.v1_16_R3.PacketPlayOutEntityMetadata;
import ru.galaxy773.bukkit.nms.interfaces.entity.GEntity;
import ru.galaxy773.bukkit.nms.interfaces.packet.entity.PacketEntityMetadata;
import ru.galaxy773.bukkit.nms.v1_16_R3.entity.GEntityBase;

public class PacketEntityMetadataImpl extends GPacketEntityBase<PacketPlayOutEntityMetadata, GEntity>
        implements PacketEntityMetadata {

    public PacketEntityMetadataImpl(final GEntity entity) {
        super(entity);
    }
    
    protected PacketPlayOutEntityMetadata init() {
        Entity entity = ((GEntityBase)this.entity).getEntityNms();
        return new PacketPlayOutEntityMetadata(entity.getId(), entity.getDataWatcher(), true);
    }
}
