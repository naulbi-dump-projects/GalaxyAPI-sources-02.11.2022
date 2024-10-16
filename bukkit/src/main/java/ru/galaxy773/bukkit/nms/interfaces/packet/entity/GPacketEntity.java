package ru.galaxy773.bukkit.nms.interfaces.packet.entity;

import ru.galaxy773.bukkit.nms.interfaces.entity.GEntity;
import ru.galaxy773.bukkit.nms.interfaces.packet.GPacket;

public interface GPacketEntity<E extends GEntity> extends GPacket {

    E getEntity();

    void setEntity(E entity);
}
