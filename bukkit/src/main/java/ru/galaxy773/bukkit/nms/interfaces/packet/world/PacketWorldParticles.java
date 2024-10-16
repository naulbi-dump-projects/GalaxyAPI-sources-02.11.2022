package ru.galaxy773.bukkit.nms.interfaces.packet.world;

import ru.galaxy773.bukkit.nms.interfaces.packet.GPacket;

public interface PacketWorldParticles extends GPacket {

    void setData(int[] data);
}
