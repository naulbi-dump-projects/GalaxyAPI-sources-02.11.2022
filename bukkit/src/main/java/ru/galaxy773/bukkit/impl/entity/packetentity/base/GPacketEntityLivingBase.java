package ru.galaxy773.bukkit.impl.entity.packetentity.base;

import com.comphenix.protocol.wrappers.WrappedDataWatcher;
import lombok.Getter;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import ru.galaxy773.bukkit.api.entity.PacketEntityLiving;
import ru.galaxy773.bukkit.api.utils.bukkit.LocationUtil;
import ru.galaxy773.bukkit.impl.entity.packetentity.packets.entity.WrapperPlayServerEntityHeadRotation;
import ru.galaxy773.bukkit.impl.entity.packetentity.packets.entity.WrapperPlayServerEntityLook;
import ru.galaxy773.bukkit.impl.entity.packetentity.packets.entity.WrapperPlayServerSpawnEntityLiving;

import java.util.UUID;

public abstract class GPacketEntityLivingBase extends GPacketEntityBase implements PacketEntityLiving {

    private final UUID uuid = UUID.randomUUID();

    @Getter
    protected final EntityEquipImpl entityEquip;

    protected GPacketEntityLivingBase(Location location) {
        super(location);

        watcher.setObject(new WrappedDataWatcher.WrappedDataWatcherObject(6, BYTE_SERIALIZER), 0);
        watcher.setObject(new WrappedDataWatcher.WrappedDataWatcherObject(7, FLOAT_SERIALIZER), 20.0);
        watcher.setObject(new WrappedDataWatcher.WrappedDataWatcherObject(8, INTEGER_SERIALIZER), 0);
        watcher.setObject(new WrappedDataWatcher.WrappedDataWatcherObject(9, BOOLEAN_SERIALIZER), false);
        watcher.setObject(new WrappedDataWatcher.WrappedDataWatcherObject(10, INTEGER_SERIALIZER), 0);

        entityEquip = new EntityEquipImpl(this);
    }

    @Override
    public void spawn(Player player) {
        if (canSee(player)) {
            return;
        }

        playersCanSee.put(player.getName(), player);

        WrapperPlayServerSpawnEntityLiving packet = new WrapperPlayServerSpawnEntityLiving();
        packet.setEntityID(entityID);
        packet.setUniqueId(uuid);
        packet.setType(getEntityType());
        packet.setX(location.getX());
        packet.setY(location.getY());
        packet.setZ(location.getZ());
        packet.setMetadata(watcher);
        packet.sendPacket(player);

        entityEquip.sendAllItems(player);
    }

    @Override
    public void setLook(float yaw, float pitch) {
        location.setYaw(yaw);
        location.setPitch(pitch);

        WrapperPlayServerEntityLook look = new WrapperPlayServerEntityLook();
        look.setEntityID(entityID);
        look.setYaw(LocationUtil.getFixRotation(yaw));
        look.setPitch(LocationUtil.getFixRotation(pitch));
        look.setOnGround(true);
        sendPacket(look);

        WrapperPlayServerEntityHeadRotation headRotation = new WrapperPlayServerEntityHeadRotation();
        headRotation.setEntityID(entityID);
        headRotation.setHeadYaw(LocationUtil.getFixRotation(yaw));
        sendPacket(headRotation);
    }
}
