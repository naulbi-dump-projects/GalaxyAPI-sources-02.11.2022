package ru.galaxy773.bukkit.impl.entity.packetentity;

import org.bukkit.Location;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import ru.galaxy773.bukkit.impl.entity.packetentity.base.GPacketEntityBase;
import ru.galaxy773.bukkit.impl.entity.packetentity.packets.entity.WrapperPlayServerEntityMetadata;
import ru.galaxy773.bukkit.impl.entity.packetentity.packets.entity.WrapperPlayServerSpawnEntity;

public class GPacketEntityItem extends GPacketEntityBase {

    public GPacketEntityItem(Location location, ItemStack itemStack) {
        super(location);

        watcher.setObject(6, CRAFT_ITEM_STACK_SERIALIZER, itemStack);
    }

    public void setItem(ItemStack item) {
        watcher.getWatchableObjects().forEach(wrappedWatchableObject -> wrappedWatchableObject.setDirtyState(false));

        watcher.setObject(6, item, true);

        sendMetaData();
    }

    @Override
    public EntityType getEntityType() {
        return EntityType.DROPPED_ITEM;
    }

    @Override
    public void spawn(Player player) {
        if (canSee(player))
            return;

        playersCanSee.put(player.getName(), player);

        WrapperPlayServerSpawnEntity packet = new WrapperPlayServerSpawnEntity();
        packet.setEntityID(entityID);
        packet.setType(WrapperPlayServerSpawnEntity.ObjectTypes.ITEM_STACK);
        packet.setObjectData(1);
        packet.sendPacket(player);

        WrapperPlayServerEntityMetadata metadata = new WrapperPlayServerEntityMetadata();
        metadata.setEntityID(entityID);
        metadata.setMetadata(watcher.getWatchableObjects());
        metadata.sendPacket(player);
    }

    @Override
    public void remove() {
        playersCanSee.values().forEach(this::destroy);
    }
}
