package ru.galaxy773.bukkit.api.entity;

import ru.galaxy773.bukkit.api.entity.depend.PacketEntity;

public interface PacketEntityLiving extends PacketEntity {

    void setLook(float yaw, float pitch);

    EntityEquip getEntityEquip();
}
