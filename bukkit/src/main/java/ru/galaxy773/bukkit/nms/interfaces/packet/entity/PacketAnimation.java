package ru.galaxy773.bukkit.nms.interfaces.packet.entity;

import ru.galaxy773.bukkit.nms.interfaces.entity.GEntity;
import ru.galaxy773.bukkit.api.entity.npc.AnimationNpcType;

public interface PacketAnimation extends GPacketEntity<GEntity> {

    AnimationNpcType getAnimation();

    void setAnimation(AnimationNpcType animation);
}
