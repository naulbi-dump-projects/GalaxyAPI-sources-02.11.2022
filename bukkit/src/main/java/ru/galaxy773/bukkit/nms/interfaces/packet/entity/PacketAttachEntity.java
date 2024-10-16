package ru.galaxy773.bukkit.nms.interfaces.packet.entity;

import ru.galaxy773.bukkit.nms.interfaces.entity.GEntity;

public interface PacketAttachEntity extends GPacketEntity<GEntity> {

    void setVehicle(GEntity vehicle);

    GEntity getVehicle();
}
