package ru.galaxy773.bukkit.nms.v1_12_R1.packet.entityplayer;

import lombok.Getter;
import net.minecraft.server.v1_12_R1.PacketPlayOutCamera;
import org.bukkit.craftbukkit.v1_12_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;
import ru.galaxy773.bukkit.nms.interfaces.packet.entityplayer.PacketCamera;
import ru.galaxy773.bukkit.nms.v1_12_R1.packet.GPacketBase;

@Getter
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
}
