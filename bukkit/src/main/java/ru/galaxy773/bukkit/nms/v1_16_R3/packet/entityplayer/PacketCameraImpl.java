package ru.galaxy773.bukkit.nms.v1_16_R3.packet.entityplayer;

import net.minecraft.server.v1_16_R3.PacketPlayOutCamera;
import org.bukkit.craftbukkit.v1_16_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;
import ru.galaxy773.bukkit.nms.interfaces.packet.entityplayer.PacketCamera;
import ru.galaxy773.bukkit.nms.v1_16_R3.packet.GPacketBase;

public class PacketCameraImpl extends GPacketBase<PacketPlayOutCamera> implements PacketCamera {

    private Player player;
    
    public PacketCameraImpl(Player player) {
        this.player = player;
    }
    
    @Override
    protected PacketPlayOutCamera init() {
        return new PacketPlayOutCamera(((CraftPlayer)player).getHandle());
    }
    
    @Override
    public void setPlayer(Player player) {
        this.player = player;
        init();
    }
    
    @Override
    public Player getPlayer() {
        return player;
    }
}
