package ru.galaxy773.bukkit.nms.v1_16_R3.packet;

import ru.galaxy773.bukkit.api.BukkitAPI;
import net.minecraft.server.v1_16_R3.EntityPlayer;
import net.minecraft.server.v1_16_R3.Packet;
import net.minecraft.server.v1_16_R3.PlayerConnection;
import org.bukkit.craftbukkit.v1_16_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;
import ru.galaxy773.bukkit.nms.interfaces.packet.GPacket;

public abstract class GPacketBase<T extends Packet<?>> implements GPacket  {

    private T packet;
    
    @Override
    public void sendPacket(Player player) {
        if (player == null || !player.isOnline()) {
            return;
        }
        if (packet == null) {
            packet = init();
        }
        if (packet == null) {
            return;
        }
        EntityPlayer handle = ((CraftPlayer)player).getHandle();
        if (handle == null) {
            return;
        }
        PlayerConnection playerConnection = handle.playerConnection;
        if (playerConnection == null) {
            return;
        }
        playerConnection.sendPacket(packet);
    }
    
    protected abstract T init();
    
    @Override
    public String toString() {
        return packet.getClass().getSimpleName();
    }
}
