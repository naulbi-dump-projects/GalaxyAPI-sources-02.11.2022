package ru.galaxy773.bukkit.impl.entity.packetentity.packets.entity;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.events.PacketEvent;
import com.comphenix.protocol.injector.PacketConstructor;
import com.comphenix.protocol.reflect.IntEnum;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import ru.galaxy773.bukkit.impl.entity.packetentity.packets.AbstractPacket;

import java.util.UUID;

public class WrapperPlayServerSpawnEntity extends AbstractPacket {
    
	public static final PacketType TYPE;
    private static PacketConstructor entityConstructor;
    
    public WrapperPlayServerSpawnEntity() {
        super(new PacketContainer(WrapperPlayServerSpawnEntity.TYPE), WrapperPlayServerSpawnEntity.TYPE);
        this.handle.getModifier().writeDefaults();
    }
    
    public WrapperPlayServerSpawnEntity(PacketContainer packet) {
        super(packet, WrapperPlayServerSpawnEntity.TYPE);
    }
    
    public WrapperPlayServerSpawnEntity(Entity entity, int type, int objectData) {
        super(fromEntity(entity, type, objectData), WrapperPlayServerSpawnEntity.TYPE);
    }
    
    private static PacketContainer fromEntity(Entity entity, int type, int objectData) {
        if (WrapperPlayServerSpawnEntity.entityConstructor == null) {
            WrapperPlayServerSpawnEntity.entityConstructor = ProtocolLibrary.getProtocolManager().createPacketConstructor(WrapperPlayServerSpawnEntity.TYPE, new Object[] { entity, type, objectData });
        }
        return WrapperPlayServerSpawnEntity.entityConstructor.createPacket(new Object[] { entity, type, objectData });
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
    
    public void setEntityID(int value) {
        this.handle.getIntegers().write(0, value);
    }
    
    public UUID getUniqueId() {
        return (UUID)this.handle.getUUIDs().read(0);
    }
    
    public void setUniqueId(UUID value) {
        this.handle.getUUIDs().write(0, value);
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
    
    public double getOptionalSpeedX() {
        return (int)this.handle.getIntegers().read(1) / 8000.0;
    }
    
    public void setOptionalSpeedX(double value) {
        this.handle.getIntegers().write(1, (int)(value * 8000.0));
    }
    
    public double getOptionalSpeedY() {
        return (int)this.handle.getIntegers().read(2) / 8000.0;
    }
    
    public void setOptionalSpeedY(double value) {
        this.handle.getIntegers().write(2, (int)(value * 8000.0));
    }
    
    public double getOptionalSpeedZ() {
        return (int)this.handle.getIntegers().read(3) / 8000.0;
    }
    
    public void setOptionalSpeedZ(double value) {
        this.handle.getIntegers().write(3, (int)(value * 8000.0));
    }
    
    public float getPitch() {
        return (int)this.handle.getIntegers().read(4) * 360.0f / 256.0f;
    }
    
    public void setPitch(float value) {
        this.handle.getIntegers().write(4, (int)(value * 256.0f / 360.0f));
    }
    
    public float getYaw() {
        return (int)this.handle.getIntegers().read(5) * 360.0f / 256.0f;
    }
    
    public void setYaw(float value) {
        this.handle.getIntegers().write(5, (int)(value * 256.0f / 360.0f));
    }
    
    public int getType() {
        return (int)this.handle.getIntegers().read(6);
    }
    
    public void setType(int value) {
        this.handle.getIntegers().write(6, value);
    }
    
    public int getObjectData() {
        return (int)this.handle.getIntegers().read(7);
    }
    
    public void setObjectData(int value) {
        this.handle.getIntegers().write(7, value);
    }
    
    static {
        TYPE = PacketType.Play.Server.SPAWN_ENTITY;
    }
    
    public static class ObjectTypes extends IntEnum
    {
        public static int BOAT = 1;
        public static int ITEM_STACK = 2;
        public static int AREA_EFFECT_CLOUD = 3;
        public static int MINECART = 10;
        public static int ACTIVATED_TNT = 50;
        public static int ENDER_CRYSTAL = 51;
        public static int TIPPED_ARROW_PROJECTILE = 60;
        public static int SNOWBALL_PROJECTILE = 61;
        public static int EGG_PROJECTILE = 62;
        public static int GHAST_FIREBALL = 63;
        public static int BLAZE_FIREBALL = 64;
        public static int THROWN_ENDERPEARL = 65;
        public static int WITHER_SKULL_PROJECTILE = 66;
        public static int SHULKER_BULLET = 67;
        public static int FALLING_BLOCK = 70;
        public static int ITEM_FRAME = 71;
        public static int EYE_OF_ENDER = 72;
        public static int THROWN_POTION = 73;
        public static int THROWN_EXP_BOTTLE = 75;
        public static int FIREWORK_ROCKET = 76;
        public static int LEASH_KNOT = 77;
        public static int ARMORSTAND = 78;
        public static int FISHING_FLOAT = 90;
        public static int SPECTRAL_ARROW = 91;
        public static int DRAGON_FIREBALL = 93;
        private static ObjectTypes INSTANCE;
        
        public static ObjectTypes getInstance() {
            return ObjectTypes.INSTANCE;
        }
        
        static {
            ObjectTypes.INSTANCE = new ObjectTypes();
        }
    }
}
