package ru.galaxy773.bukkit.impl.entity.customstand;

import io.netty.util.internal.ConcurrentSet;
import lombok.Getter;
import lombok.NonNull;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.util.EulerAngle;
import ru.galaxy773.bukkit.api.entity.EntityEquip;
import ru.galaxy773.bukkit.api.entity.stand.CustomStand;
import ru.galaxy773.bukkit.api.utils.bukkit.LocationUtil;
import ru.galaxy773.bukkit.api.utils.bukkit.WorldUtil;
import ru.galaxy773.bukkit.api.utils.math.CraftVector;
import ru.galaxy773.bukkit.impl.entity.packetentity.base.PacketEntityUtil;
import ru.galaxy773.bukkit.impl.entity.tracker.TrackerEntity;
import ru.galaxy773.bukkit.nms.api.NmsAPI;
import ru.galaxy773.bukkit.nms.interfaces.NmsManager;
import ru.galaxy773.bukkit.nms.interfaces.entity.GEntityArmorStand;
import ru.galaxy773.bukkit.nms.interfaces.entity.GEntityItem;
import ru.galaxy773.bukkit.nms.interfaces.packet.GPacket;
import ru.galaxy773.bukkit.nms.interfaces.packet.PacketContainer;
import ru.galaxy773.bukkit.nms.interfaces.packet.entity.*;
import ru.galaxy773.bukkit.nms.types.EntitySpawnType;

import java.util.*;

public class CraftStand implements CustomStand, TrackerEntity {
	
    private static final NmsManager NMS_MANAGER = NmsAPI.getManager();
    private static final PacketContainer PACKET_CONTAINER = NmsAPI.getManager().getPacketContainer();
    private final StandManager standManager;
    private final Set<String> playersCanSee = new ConcurrentSet<>();
    private final GEntityArmorStand entity;
    private final EntityEquip equip;
    @Getter
    private final JavaPlugin plugin;
    private Location location;
    @Getter
    private Player owner;
    private GEntityItem passenger;
    private boolean visionAll;
    
    public CraftStand(JavaPlugin javaPlugin, StandManager standManager, @NonNull Location location) {
        WorldUtil.checkAndLoadChunk(location.getWorld(), location.getBlockX(), location.getBlockZ());
    	this.plugin = javaPlugin;
        this.standManager = standManager;
        this.location = location.clone();
        entity = NMS_MANAGER.createGEntity(GEntityArmorStand.class, location);
        entity.setNoGravity(true);
        entity.setCollides(false);
        equip = new StandEquip(this);
        standManager.addStand(this);
    }

    @Override
    public boolean isVisibleTo(Player player) {
        return playersCanSee.contains(player.getName());
    }

    @Override
    public Collection<String> getVisiblePlayers() {
        if (!visionAll) {
            return new HashSet<>(playersCanSee);
        }
        List<String> playersList = new ArrayList<>();
        Bukkit.getOnlinePlayers().forEach(pl -> playersList.add(pl.getName()));
        return playersList;
    }

    @Override
    public void showTo(Player player) {
        if (player == null) {
            return;
        }
        String name = player.getName();
        if (playersCanSee.contains(name)) {
            return;
        }
        spawn(player);
        playersCanSee.add(name);
    }

    @Override
    public void removeTo(Player player) {
        if (player == null) {
            return;
        }
        playersCanSee.remove(player.getName());
        destroy(player);
    }

    @Override
    public void hideAll() {
        setPublic(false);
        Bukkit.getOnlinePlayers().forEach(this::removeTo);
    }

    @Override
    public int getEntityID() {
        return entity.getEntityID();
    }

    @Override
    public Location getLocation() {
        return location.clone();
    }

    public GEntityArmorStand getEntity() {
        return entity;
    }

    @Override
    public boolean isSmall() {
        return entity.isSmall();
    }

    @Override
    public boolean hasArms() {
        return entity.hasArms();
    }

    @Override
    public boolean hasPassenger() {
        return passenger != null;
    }

    @Override
    public boolean hasBasePlate() {
        return !entity.hasBasePlate();
    }

    @Override
    public boolean isInvisible() {
        return entity.isInvisible();
    }

    @Override
    public boolean isPublic() {
        return visionAll;
    }

    @Override
    public void setPublic(boolean vision) {
        if (this.visionAll == vision) {
            return;
        }
        if (vision) {
            Bukkit.getOnlinePlayers().forEach(this::showTo);
        } else {
            Bukkit.getOnlinePlayers().forEach(this::removeTo);
        }
        this.visionAll = vision;

    }

    @Override
    public void removePassenger() {
        if (!hasPassenger()) {
            return;
        }
        entity.removePassenger();
        int passengerID = passenger.getEntityID();
        PacketEntityDestroy packet = PACKET_CONTAINER.getEntityDestroyPacket(passengerID);
        sendPacket(packet);
        passenger = null;
    }

    @Override
    public void setArms(boolean arms) {
        entity.setArms(arms);
        sendData();
    }

    @Override
    public void setSmall(boolean small) {
        entity.setSmall(small);
        sendData();
    }

    @Override
    public void setItemPassenger(ItemStack itemPassenger) {
        if (itemPassenger == null) {
            return;
        }
        removePassenger();
        GEntityItem passenger = NMS_MANAGER.createGEntity(GEntityItem.class, location);
        passenger.setItemStack(itemPassenger);
        this.passenger = passenger;
        entity.setPassenger(passenger);
        PacketSpawnEntity itemPacket = PACKET_CONTAINER.getSpawnEntityPacket(passenger,
                EntitySpawnType.ITEM_STACK, 1);
        sendPacket(itemPacket);
        PacketMount finalPacket = PACKET_CONTAINER.getMountPacket(entity);
        sendPacket(finalPacket);
        PacketEntityMetadata metadata = PACKET_CONTAINER.getEntityMetadataPacket(passenger);
        sendPacket(metadata);
    }

    @Override
    public void setBasePlate(boolean basePlate) {
        entity.setBasePlate(!basePlate);
        sendData();
    }

    @Override
    public void setInvisible(boolean invisible) {
        entity.setInvisible(invisible);
        sendData();
    }

    @Override
    public void onTeleport(Location location) {
        if (location.equals(this.location)) {
            return;
        }
        this.location = location;
        entity.setLocation(location);
        sendPacket(PACKET_CONTAINER.getEntityTeleportPacket(entity));
    }

    @Override
    public void setOwner(Player owner) {
        this.owner = owner;
        showTo(owner);
    }

    @Override
    public void setCustomName(String name) {
        if (!entity.getCustomNameVisible()) {
            entity.setCustomNameVisible(true);
        }
        entity.setCustomName(name);
        sendData();
    }

    @Override
    public void setLook(float yaw, float pitch) {
        location.setYaw(yaw);
        location.setPitch(pitch);
        entity.setLocation(location);
        PacketEntityLook packet = PACKET_CONTAINER.getEntityLookPacket(entity,
                LocationUtil.getFixRotation(yaw), LocationUtil.getFixRotation(pitch));
        sendPacket(packet);
    }

    @Override
    public EntityEquip getEntityEquip() {
        return equip;
    }

    @Override
    public void setHeadPose(CraftVector vector) {
        entity.setHeadPose(vector);
        sendData();
    }

    @Override
    public void setBodyPose(CraftVector vector) {
        entity.setBodyPose(vector);
        sendData();
    }

    @Override
    public void setLeftArmPose(CraftVector vector) {
        entity.setLeftArmPose(vector);
        sendData();
    }

    @Override
    public void setRightArmPose(CraftVector vector) {
        entity.setRightArmPose(vector);
        sendData();
    }

    @Override
    public void setRightLegPose(CraftVector vector) {
        entity.setRightLegPose(vector);
        sendData();
    }

    @Override
    public void setLeftLegPose(CraftVector vector) {
        entity.setLeftLegPose(vector);
        sendData();
    }

    @Override
    public void setHeadPose(EulerAngle pose) {
        setHeadPose(PacketEntityUtil.toNMS(pose));
    }

    @Override
    public void setBodyPose(EulerAngle pose) {
        setBodyPose(PacketEntityUtil.toNMS(pose));
    }

    @Override
    public void setLeftArmPose(EulerAngle pose) {
        setLeftArmPose(PacketEntityUtil.toNMS(pose));
    }

    @Override
    public void setRightArmPose(EulerAngle pose) {
        setRightArmPose(PacketEntityUtil.toNMS(pose));
    }

    @Override
    public void setRightLegPose(EulerAngle pose) {
        setRightLegPose(PacketEntityUtil.toNMS(pose));
    }

    @Override
    public void setLeftLegPose(EulerAngle pose) {
        setLeftLegPose(PacketEntityUtil.toNMS(pose));
    }

    @Override
    public EulerAngle getHeadPose() {
        return PacketEntityUtil.fromNMS(entity.getHeadPose());
    }

    @Override
    public EulerAngle getBodyPose() {
        return PacketEntityUtil.fromNMS(entity.getBodyPose());
    }

    @Override
    public EulerAngle getLeftArmPose() {
        return PacketEntityUtil.fromNMS(entity.getLeftArmPose());
    }

    @Override
    public EulerAngle getRightArmPose() {
        return PacketEntityUtil.fromNMS(entity.getRightArmPose());
    }

    @Override
    public EulerAngle getRightLegPose() {
        return PacketEntityUtil.fromNMS(entity.getRightLegPose());
    }

    @Override
    public EulerAngle getLeftLegPose() {
        return PacketEntityUtil.fromNMS(entity.getLeftLegPose());
    }

    @Override
    public void remove() {
        for (String name : playersCanSee) {
            destroy(Bukkit.getPlayerExact(name));
        }
        standManager.removeStand(this);
    }

    @Override
    public String toString() {
        return "CustomStand {" + location + "}";
    }

    @Override
    public void spawn(Player player) {
        if (player == null || player.getWorld() != location.getWorld()) {
            return;
        }
        PacketSpawnEntityLiving packet = PACKET_CONTAINER.getSpawnEntityLivingPacket(entity);
        packet.sendPacket(player);

        if (entity.hasPassenger()) {
            PacketSpawnEntity itemPacket = PACKET_CONTAINER.getSpawnEntityPacket(passenger,
                    EntitySpawnType.ITEM_STACK, 1);
            itemPacket.sendPacket(player);
            PacketMount finalPacket = PACKET_CONTAINER.getMountPacket(entity);
            finalPacket.sendPacket(player);
            PacketEntityMetadata metadata = PACKET_CONTAINER.getEntityMetadataPacket(passenger);
            metadata.sendPacket(player);
        }
        equip.getItemsEquip().forEach((equipType, itemStack) -> {
            PacketEntityEquipment equipPacket = PACKET_CONTAINER.getEntityEquipmentPacket(entity, equipType, itemStack);
            equipPacket.sendPacket(player);
        });
    }

    @Override
    public void destroy(Player player) {
        int standID = entity.getEntityID();
        if (entity.hasPassenger()) {
            int passengerID = passenger.getEntityID();
            PACKET_CONTAINER.getEntityDestroyPacket(passengerID, standID).sendPacket(player);
            return;
        }
        PACKET_CONTAINER.getEntityDestroyPacket(standID).sendPacket(player);
    }

    @Override
    public boolean canSee(Player player) {
        if (player == null) {
            return false;
        }
        return (visionAll || playersCanSee.contains(player.getName()))
                && location.getWorld() == player.getLocation().getWorld();
    }

    @Override
    public boolean isHeadLook() {
        return false;
    }

    @Override
    public void sendHeadRotation(Player player, float yaw, float pitch) {
    }

    @Override
    public Set<String> getHeadPlayers() {
        return Collections.emptySet();
    }

    private void sendData() {
        PacketEntityMetadata packet = PACKET_CONTAINER.getEntityMetadataPacket(entity);
        sendPacket(packet);
    }

    protected void sendPacket(GPacket packet) {
        for (String name : getVisiblePlayers()) {
            Player player = Bukkit.getPlayerExact(name);
            if (player != null && player.getWorld() == location.getWorld()) {
                packet.sendPacket(player);
            }
        }
    }
}
