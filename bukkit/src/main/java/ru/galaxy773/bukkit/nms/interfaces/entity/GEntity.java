package ru.galaxy773.bukkit.nms.interfaces.entity;

import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.inventory.ItemStack;
import ru.galaxy773.bukkit.api.entity.EquipType;

import java.util.UUID;

public interface GEntity {
	
    void setLocation(Location location);
    Location getLocation();

    int getEntityID();

    void setNoGravity(boolean gravity);

    boolean hasPassenger();
    void setPassenger(GEntity dEntity);
    void removePassenger();

    boolean getCustomNameVisible();
    void setCustomName(String name);
    void setCustomNameVisible(boolean visible);

    boolean isInvisible();

    boolean isOnGround();

    void watch(int type, byte value);

    UUID getUniqueID();

    EntityType getType();
    int getEntityTypeID();

    void setEquipment(EquipType equipment, ItemStack itemStack);

    void setGlowing(boolean glowing);

    Entity getBukkitEntity();
}
