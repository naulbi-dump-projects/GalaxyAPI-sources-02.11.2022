package ru.galaxy773.bukkit.nms.interfaces.packet.entityplayer;

import ru.galaxy773.bukkit.nms.interfaces.packet.GPacket;
import org.bukkit.entity.Player;

public interface PacketCamera extends GPacket {

    Player getPlayer();

    void setPlayer(Player player);
}
