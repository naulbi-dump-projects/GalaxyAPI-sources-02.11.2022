package ru.galaxy773.bukkit.nms.v1_16_R3.packet.entityplayer;

import net.minecraft.server.v1_16_R3.EntityHuman;
import net.minecraft.server.v1_16_R3.PacketPlayOutNamedEntitySpawn;
import ru.galaxy773.bukkit.nms.interfaces.entity.GEntityPlayer;
import ru.galaxy773.bukkit.nms.interfaces.packet.entityplayer.PacketNamedEntitySpawn;
import ru.galaxy773.bukkit.nms.v1_16_R3.entity.GEntityLivingBase;
import ru.galaxy773.bukkit.nms.v1_16_R3.packet.entity.GPacketEntityBase;

public class PacketNamedEntitySpawnImpl extends GPacketEntityBase<PacketPlayOutNamedEntitySpawn, GEntityPlayer>
        implements PacketNamedEntitySpawn {

    public PacketNamedEntitySpawnImpl(GEntityPlayer entity) {
        super(entity);
    }
    
    protected PacketPlayOutNamedEntitySpawn init() {
        return new PacketPlayOutNamedEntitySpawn(((GEntityLivingBase<EntityHuman>)entity).getEntityNms());
    }
}
