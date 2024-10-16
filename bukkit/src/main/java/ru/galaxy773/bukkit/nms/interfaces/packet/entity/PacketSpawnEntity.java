package ru.galaxy773.bukkit.nms.interfaces.packet.entity;

import ru.galaxy773.bukkit.nms.interfaces.entity.GEntity;
import ru.galaxy773.bukkit.nms.types.EntitySpawnType;

public interface PacketSpawnEntity extends GPacketEntity<GEntity> {

    EntitySpawnType getEntitySpawnType();

    void setEntitySpawnType(EntitySpawnType entitySpawnType);

    int getObjectData();

    void setObjectData(int objectData);
}
