package ru.galaxy773.bukkit.nms.v1_12_R1.packet.entityplayer;

import net.minecraft.server.v1_12_R1.PacketPlayOutNamedEntitySpawn;
import ru.galaxy773.bukkit.nms.interfaces.entity.GEntityPlayer;
import ru.galaxy773.bukkit.nms.interfaces.packet.entityplayer.PacketNamedEntitySpawn;
import ru.galaxy773.bukkit.nms.v1_12_R1.entity.GEntityPlayerImpl;
import ru.galaxy773.bukkit.nms.v1_12_R1.packet.entity.GPacketEntityBase;

public class PacketNamedEntitySpawnImpl extends GPacketEntityBase<PacketPlayOutNamedEntitySpawn, GEntityPlayer>
        implements PacketNamedEntitySpawn {

    public PacketNamedEntitySpawnImpl(GEntityPlayer entity) {
        super(entity);
    }

    @Override
    protected PacketPlayOutNamedEntitySpawn init() {
        return new PacketPlayOutNamedEntitySpawn(((GEntityPlayerImpl)entity).getEntityNms());
    }
}
