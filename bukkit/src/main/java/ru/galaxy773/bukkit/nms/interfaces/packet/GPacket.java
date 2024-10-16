package ru.galaxy773.bukkit.nms.interfaces.packet;

import org.bukkit.entity.Player;

import java.util.Arrays;

public interface GPacket {

    void sendPacket(Player player);

    default void sendPacket(Player... players) {
        Arrays.asList(players).forEach(this::sendPacket);
    }

    @Override
    String toString();
}
