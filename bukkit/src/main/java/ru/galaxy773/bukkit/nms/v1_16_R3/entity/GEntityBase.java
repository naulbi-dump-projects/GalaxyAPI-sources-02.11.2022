package ru.galaxy773.bukkit.nms.v1_16_R3.entity;

import net.minecraft.server.v1_16_R3.ChatMessage;
import net.minecraft.server.v1_16_R3.DataWatcherObject;
import net.minecraft.server.v1_16_R3.DataWatcherRegistry;
import net.minecraft.server.v1_16_R3.EnumItemSlot;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_16_R3.CraftWorld;
import org.bukkit.craftbukkit.v1_16_R3.inventory.CraftItemStack;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.inventory.ItemStack;
import ru.galaxy773.bukkit.api.entity.EquipType;
import ru.galaxy773.bukkit.nms.interfaces.entity.GEntity;

import java.util.UUID;

public abstract class GEntityBase<T extends net.minecraft.server.v1_16_R3.Entity> implements GEntity {

    protected T entity;

    protected GEntityBase(T entity) {
        this.entity = entity;
    }

    @Override
    public void setLocation(Location location) {
        entity.setLocation(location.getX(), location.getY(), location.getZ(), location.getYaw(), location.getPitch());
    }

    @Override
    public Location getLocation() {
        CraftWorld craftWorld = entity.world.getWorld();
        return new Location(craftWorld, entity.locX(), entity.locY(), entity.locZ(), entity.yaw, entity.pitch);
    }

    @Override
    public int getEntityID() {
        return entity.getId();
    }

    @Override
    public void setNoGravity(boolean gravity) {
        entity.setNoGravity(gravity);
    }

    @Override
    public boolean hasPassenger() {
        return entity.passengers != null && entity.passengers.size() > 0;
    }

    @Override
    public void setPassenger(GEntity tEntity) {
        removePassenger();
        net.minecraft.server.v1_16_R3.Entity entity = ((GEntityBase) tEntity).getEntityNms();
        this.entity.passengers.add(entity);
    }

    public T getEntityNms() {
        return entity;
    }

    @Override
    public String toString() {
        return entity.toString();
    }

    @Override
    public void removePassenger() {
        if (!hasPassenger())
            return;

        entity.passengers.clear();
    }

    @Override
    public boolean getCustomNameVisible() {
        return entity.getCustomNameVisible();
    }

    @Override
    public void setCustomName(String name) {
        entity.setCustomName(new ChatMessage(name, new Object[0]));
    }

    @Override
    public void setCustomNameVisible(boolean visible) {
        entity.setCustomNameVisible(visible);
    }

    @Override
    public boolean isInvisible() {
        return entity.isInvisible();
    }

    @Override
    public boolean isOnGround() {
        return entity.isOnGround();
    }

    @Override
    public void watch(int type, byte value) {
        entity.getDataWatcher().set(new DataWatcherObject<>(type, DataWatcherRegistry.a), value);
    }

    @Override
    public UUID getUniqueID() {
        return entity.getUniqueID();
    }

    @Override
    public void setEquipment(EquipType equipment, ItemStack itemStack) {
        entity.setSlot(EnumItemSlot.valueOf(equipment.name()), CraftItemStack.asNMSCopy(itemStack));
    }

    @Override
    public void setGlowing(boolean glowing) {
        entity.glowing = glowing;
        if (entity.getFlag(6) == glowing)
            return;

        entity.setFlag(6, glowing);
    }

    @Override
    public EntityType getType() {
        return getBukkitEntity().getType();
    }

    @Override
    public int getEntityTypeID() {
        return GEntityLivingBase.getCustomEntityRegistry().a(this.entity.getEntityType());
    }

    @Override
    public Entity getBukkitEntity() {
        return entity.getBukkitEntity();
    }
}
