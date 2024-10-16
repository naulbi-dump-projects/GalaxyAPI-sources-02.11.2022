package ru.galaxy773.bukkit.nms.interfaces.packet.entity;

import ru.galaxy773.bukkit.nms.interfaces.entity.GEntity;

public interface PacketEntityLook extends GPacketEntity<GEntity> {

    void setPitch(byte pitch);

    void setYaw(byte yaw);

}
