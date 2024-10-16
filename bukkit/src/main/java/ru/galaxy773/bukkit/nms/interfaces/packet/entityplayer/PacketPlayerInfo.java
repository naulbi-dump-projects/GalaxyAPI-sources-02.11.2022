package ru.galaxy773.bukkit.nms.interfaces.packet.entityplayer;

import ru.galaxy773.bukkit.nms.interfaces.entity.GEntityPlayer;
import ru.galaxy773.bukkit.nms.interfaces.packet.entity.GPacketEntity;
import ru.galaxy773.bukkit.nms.types.PlayerInfoActionType;

public interface PacketPlayerInfo extends GPacketEntity<GEntityPlayer> {

    void setPlayerInfoAction(PlayerInfoActionType actionType);

    PlayerInfoActionType getActionType();
}
