package ru.galaxy773.bukkit.impl.entity.packetentity.packets.entity;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.events.PacketEvent;
import com.comphenix.protocol.injector.PacketConstructor;
import com.comphenix.protocol.wrappers.WrappedDataWatcher;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import ru.galaxy773.bukkit.impl.entity.packetentity.packets.AbstractPacket;

import java.util.UUID;

@SuppressWarnings("deprecation")
public class WrapperPlayServerSpawnEntityLiving extends AbstractPacket {
	
    public static final PacketType TYPE;
    private static PacketConstructor entityConstructor;
    
    public WrapperPlayServerSpawnEntityLiving() {
        super(new PacketContainer(WrapperPlayServerSpawnEntityLiving.TYPE), WrapperPlayServerSpawnEntityLiving.TYPE);
        this.handle.getModifier().writeDefaults();
    }
    
    public WrapperPlayServerSpawnEntityLiving(PacketContainer packet) {
        super(packet, WrapperPlayServerSpawnEntityLiving.TYPE);
    }
    
    public WrapperPlayServerSpawnEntityLiving(Entity entity) {
        super(fromEntity(entity), WrapperPlayServerSpawnEntityLiving.TYPE);
    }
    
    private static PacketContainer fromEntity(Entity entity) {
        if (WrapperPlayServerSpawnEntityLiving.entityConstructor == null) {
            WrapperPlayServerSpawnEntityLiving.entityConstructor = ProtocolLibrary.getProtocolManager().createPacketConstructor(WrapperPlayServerSpawnEntityLiving.TYPE, new Object[] { entity });
        }
        return WrapperPlayServerSpawnEntityLiving.entityConstructor.createPacket(new Object[] { entity });
    }
    
    public int getEntityID() {
        return (int)this.handle.getIntegers().read(0);
    }
    
    public Entity getEntity(World world) {
        return (Entity)this.handle.getEntityModifier(world).read(0);
    }
    
    public Entity getEntity(PacketEvent event) {
        return this.getEntity(event.getPlayer().getWorld());
    }
    
    public UUID getUniqueId() {
        return (UUID)this.handle.getUUIDs().read(0);
    }
    
    public void setUniqueId(UUID value) {
        this.handle.getUUIDs().write(0, value);
    }
    
    public void setEntityID(int value) {
        this.handle.getIntegers().write(0, value);
    }
    
	public EntityType getType() {
        return EntityType.fromId((int)this.handle.getIntegers().read(1));
    }
    
    public void setType(EntityType value) {
        this.handle.getIntegers().write(1, (int)value.getTypeId());
    }
    
    public double getX() {
        return (double)this.handle.getDoubles().read(0);
    }
    
    public void setX(double value) {
        this.handle.getDoubles().write(0, value);
    }
    
    public double getY() {
        return (double)this.handle.getDoubles().read(1);
    }
    
    public void setY(double value) {
        this.handle.getDoubles().write(1, value);
    }
    
    public double getZ() {
        return (double)this.handle.getDoubles().read(2);
    }
    
    public void setZ(double value) {
        this.handle.getDoubles().write(2, value);
    }
    
    public float getYaw() {
        return (byte)this.handle.getBytes().read(0) * 360.0f / 256.0f;
    }
    
    public void setYaw(float value) {
        this.handle.getBytes().write(0, (byte)(value * 256.0f / 360.0f));
    }
    
    public float getPitch() {
        return (byte)this.handle.getBytes().read(1) * 360.0f / 256.0f;
    }
    
    public void setPitch(float value) {
        this.handle.getBytes().write(1, (byte)(value * 256.0f / 360.0f));
    }
    
    public float getHeadPitch() {
        return (byte)this.handle.getBytes().read(2) * 360.0f / 256.0f;
    }
    
    public void setHeadPitch(float value) {
        this.handle.getBytes().write(2, (byte)(value * 256.0f / 360.0f));
    }
    
    public double getVelocityX() {
        return (int)this.handle.getIntegers().read(2) / 8000.0;
    }
    
    public void setVelocityX(double value) {
        this.handle.getIntegers().write(2, (int)(value * 8000.0));
    }
    
    public double getVelocityY() {
        return (int)this.handle.getIntegers().read(3) / 8000.0;
    }
    
    public void setVelocityY(double value) {
        this.handle.getIntegers().write(3, (int)(value * 8000.0));
    }
    
    public double getVelocityZ() {
        return (int)this.handle.getIntegers().read(4) / 8000.0;
    }
    
    public void setVelocityZ(double value) {
        this.handle.getIntegers().write(4, (int)(value * 8000.0));
    }
    
    public WrappedDataWatcher getMetadata() {
        return (WrappedDataWatcher)this.handle.getDataWatcherModifier().read(0);
    }
    
    public void setMetadata(WrappedDataWatcher value) {
        this.handle.getDataWatcherModifier().write(0, value);
    }
    
    static {
        TYPE = PacketType.Play.Server.SPAWN_ENTITY_LIVING;
    }
}
