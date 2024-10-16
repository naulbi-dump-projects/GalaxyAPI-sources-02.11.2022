package ru.galaxy773.bukkit.nms.v1_16_R3.packet.entity;

import net.minecraft.server.v1_16_R3.Entity;
import net.minecraft.server.v1_16_R3.PacketPlayOutSpawnEntity;
import ru.galaxy773.bukkit.nms.interfaces.entity.GEntity;
import ru.galaxy773.bukkit.nms.interfaces.packet.entity.PacketSpawnEntity;
import ru.galaxy773.bukkit.nms.types.EntitySpawnType;
import ru.galaxy773.bukkit.nms.v1_16_R3.entity.GEntityBase;

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
    
    protected PacketPlayOutSpawnEntity init() {
        Entity entity = ((GEntityBase)this.entity).getEntityNms();
        return new PacketPlayOutSpawnEntity(entity, this.objectData);
    }
    
    @Override
    public EntitySpawnType getEntitySpawnType() {
        return entitySpawnType;
    }
    
    @Override
    public int getObjectData() {
        return objectData;
    }
}
