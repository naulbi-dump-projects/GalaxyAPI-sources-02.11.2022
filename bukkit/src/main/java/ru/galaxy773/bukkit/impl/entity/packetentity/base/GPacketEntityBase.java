package ru.galaxy773.bukkit.impl.entity.packetentity.base;

import com.comphenix.protocol.wrappers.Vector3F;
import com.comphenix.protocol.wrappers.WrappedDataWatcher;
import com.comphenix.protocol.wrappers.WrappedWatchableObject;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import ru.galaxy773.bukkit.api.entity.depend.PacketEntity;
import ru.galaxy773.bukkit.impl.entity.packetentity.packets.AbstractPacket;
import ru.galaxy773.bukkit.impl.entity.packetentity.packets.entity.WrapperPlayServerEntityDestroy;
import ru.galaxy773.bukkit.impl.entity.packetentity.packets.entity.WrapperPlayServerEntityMetadata;
import ru.galaxy773.bukkit.impl.entity.packetentity.packets.entity.WrapperPlayServerEntityTeleport;
import ru.galaxy773.bukkit.impl.entity.tracker.TrackerEntity;

import javax.annotation.Nullable;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public abstract class GPacketEntityBase implements PacketEntity, TrackerEntity {

    protected static final WrappedDataWatcher.Serializer BYTE_SERIALIZER = WrappedDataWatcher.Registry.get(Byte.class);
    protected static final WrappedDataWatcher.Serializer INTEGER_SERIALIZER = WrappedDataWatcher.Registry.get(Integer.class);
    protected static final WrappedDataWatcher.Serializer BOOLEAN_SERIALIZER = WrappedDataWatcher.Registry.get(Boolean.class);
    protected static final WrappedDataWatcher.Serializer STRING_SERIALIZER = WrappedDataWatcher.Registry.get(String.class);
    protected static final WrappedDataWatcher.Serializer FLOAT_SERIALIZER = WrappedDataWatcher.Registry.get(Float.class);
    protected static final WrappedDataWatcher.Serializer VECTOR_3F_SERIALIZER = WrappedDataWatcher.Registry.get(Vector3F.getMinecraftClass());
    protected static final WrappedDataWatcher.Serializer CRAFT_ITEM_STACK_SERIALIZER = WrappedDataWatcher.Registry.getItemStackSerializer(false);
    @Getter
    protected final int entityID;
    @Getter
    protected final WrappedDataWatcher watcher;
    protected final Map<String, Player> playersCanSee = new ConcurrentHashMap<>();
    protected Location location;
    protected Player owner;
    protected boolean visionAll;
    
    protected GPacketEntityBase(Location location) {
        this.entityID = PacketEntityUtil.getRandomEntityId();
        this.location = location;

        this.watcher = new WrappedDataWatcher();
        watcher.setObject(new WrappedDataWatcher.WrappedDataWatcherObject(0, BYTE_SERIALIZER), 0);
        watcher.setObject(new WrappedDataWatcher.WrappedDataWatcherObject(1, INTEGER_SERIALIZER), 300);
        watcher.setObject(new WrappedDataWatcher.WrappedDataWatcherObject(2, STRING_SERIALIZER), "");
        watcher.setObject(new WrappedDataWatcher.WrappedDataWatcherObject(3, BOOLEAN_SERIALIZER), false);
        watcher.setObject(new WrappedDataWatcher.WrappedDataWatcherObject(4, BOOLEAN_SERIALIZER), false);
        watcher.setObject(new WrappedDataWatcher.WrappedDataWatcherObject(5, BOOLEAN_SERIALIZER), false);

        this.owner = null;
        this.visionAll = false;
    }

    private boolean getFlag(int flag) {
        return ((Byte)this.watcher.getWatchableObject(0).getValue() & 1 << flag) != 0;
    }

    private void setFlag(int flag, boolean value) {
    	if (getFlag(flag) == value) { 
            return;
        }
        watcher.getWatchableObjects().forEach(wrappedWatchableObject -> wrappedWatchableObject.setDirtyState(false));
        byte oldValue = (Byte)this.watcher.getWatchableObject(0).getValue();
        if (value) {
            watcher.setObject(0, (byte)(oldValue | 1 << flag), true);
        } else {
            watcher.setObject(0, (byte)(oldValue & ~(1 << flag)), true);
        }

        sendMetaData();
    }

    public boolean isInvisible() {
        return getFlag(5);
    }

    public void setInvisible(boolean invisible) {
        setFlag(5, invisible);
    }

    public boolean isGlowing() {
        return getFlag(6);
    }

    public void setGlowing(boolean glowing) {
        setFlag(6, glowing);
    }

    public boolean isNoGravity() {
        return (boolean) watcher.getWatchableObject(5).getValue();
    }

    public void setNoGravity(boolean gravity) {
        if (isNoGravity() == gravity)
            return;

        watcher.getWatchableObjects().forEach(wrappedWatchableObject -> wrappedWatchableObject.setDirtyState(false));
        watcher.setObject(5, gravity, true);

        sendMetaData();
    }

    public void setCustomName(String name) {
        watcher.getWatchableObjects().forEach(wrappedWatchableObject -> wrappedWatchableObject.setDirtyState(false));
        if (name.length() > 256) {
            name = name.substring(0, 256);
        }
        watcher.setObject(2, name, true);

        if (!(Boolean) watcher.getWatchableObject(3).getValue()) {
            watcher.setObject(3, true, true);
        }

        sendMetaData();
    }

    @Override
    public final Location getLocation() {
        return location.clone();
    }

    @Override
    public final void onTeleport(Location location) {
        if (location.equals(this.location)) {
            return;
        }

        this.location = location;
        WrapperPlayServerEntityTeleport packet = new WrapperPlayServerEntityTeleport();
        packet.setEntityID(entityID);
        packet.setX(location.getX());
        packet.setY(location.getY());
        packet.setZ(location.getZ());
        packet.setYaw(location.getYaw());
        packet.setPitch(location.getPitch());
        packet.setOnGround(false);

        sendPacket(packet);
    }

    @Override
    public boolean isVisibleTo(Player player) {
        return playersCanSee.containsKey(player.getName()) || visionAll;
    }

    @Override
    public Collection<String> getVisiblePlayers() {
        return new ArrayList<>(playersCanSee.keySet());
    }

    @Override
    public void showTo(Player player) {
        if (canSee(player)) {
            return;
        }

        spawn(player);
    }

    @Override
    public void removeTo(Player player) {
        if (player == null) {
            return;
        }

        Player remove = playersCanSee.remove(player.getName());
        if (remove == null) {
            return;
        }

        this.destroy(player);
    }

    @Override
    public boolean canSee(Player player) {
        if (player == null)
            return false;
        return (visionAll || playersCanSee.containsKey(player.getName()))
                && location.getWorld() == player.getLocation().getWorld();
    }

    @Override
    public boolean isHeadLook() {
        return false;
    }

    @Override
    public void hideAll() {
        visionAll = false;
        Bukkit.getOnlinePlayers().forEach(this::removeTo);
    }
    @Override
    public final Player getOwner() {
        return owner;
    }

    @Override
    public void destroy(Player player) {
        WrapperPlayServerEntityDestroy packet = new WrapperPlayServerEntityDestroy();
        packet.setEntityIds(new int[]{entityID});
        packet.sendPacket(player);
    }

    @Override
    public void setOwner(Player owner) {
        if (owner == this.owner)
            return;

        if (canSee(owner))
            return;

        spawn(owner);
    }

    @Override
    public boolean isPublic() {
        return visionAll;
    }

    @Override
    public void setPublic(boolean vision) {
        if (this.visionAll == vision)
            return;

        this.visionAll = vision;

        if (vision) {
            Bukkit.getOnlinePlayers().forEach(this::showTo);
        } else {
            Bukkit.getOnlinePlayers().forEach(this::removeTo);
        }
    }
    
    @Override
    public void sendHeadRotation(Player player, float yaw, float pitch) {
    }

    @Override
    public Set<String> getHeadPlayers() {
        return null;
    }

    public void sendMetaData() {
        WrapperPlayServerEntityMetadata metadata = new WrapperPlayServerEntityMetadata();
        metadata.setEntityID(entityID);
        List<WrappedWatchableObject> data = new ArrayList<>();
        watcher.getWatchableObjects().forEach(wrappedWatchableObject -> {
            if (wrappedWatchableObject.getDirtyState()) {
                data.add(wrappedWatchableObject);
            }
        });
        metadata.setMetadata(data);
        sendPacket(metadata);
    }

    protected void sendPacket(AbstractPacket packet) {
        for (Player player : playersCanSee.values()) {
            packet.sendPacket(player);
        }
    }

    public abstract EntityType getEntityType();
}
