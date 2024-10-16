package ru.galaxy773.bukkit.nms.v1_12_R1.packet.entity;

import lombok.Getter;
import net.minecraft.server.v1_12_R1.PacketPlayOutSpawnEntity;
import ru.galaxy773.bukkit.nms.interfaces.entity.GEntity;
import ru.galaxy773.bukkit.nms.interfaces.packet.entity.PacketSpawnEntity;
import ru.galaxy773.bukkit.nms.types.EntitySpawnType;
import ru.galaxy773.bukkit.nms.v1_12_R1.entity.GEntityBase;

@Getter
public class PacketSpawnEntityImpl extends GPacketEntityBase<PacketPlayOutSpawnEntity, GEntity>
        implements PacketSpawnEntity {

    private EntitySpawnType entitySpawnType;
    private int objectData;

    public PacketSpawnEntityImpl(GEntity entity, EntitySpawnType entitySpawnType, int objectData) {
        super(entity);
        this.entitySpawnType = entitySpawnType;
        this.objectData = objectData;
    }

    @Override
    public void setEntitySpawnType(EntitySpawnType entitySpawnType) {
        this.entitySpawnType = entitySpawnType;
        init();
    }

    @Override
    public void setObjectData(int objectData) {
        this.objectData = objectData;
        init();
    }

    @Override
    protected PacketPlayOutSpawnEntity init() {
        return new PacketPlayOutSpawnEntity(((GEntityBase<?>)entity).getEntityNms(), entitySpawnType.getId(), objectData);
    }
}
