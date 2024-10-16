package ru.galaxy773.bukkit.nms.v1_16_R3.entity;

import net.minecraft.server.v1_16_R3.EntityCow;
import net.minecraft.server.v1_16_R3.EntityTypes;
import net.minecraft.server.v1_16_R3.World;
import ru.galaxy773.bukkit.nms.interfaces.entity.GEntityCow;

public class GEntityCowImpl extends GEntityLivingBase<EntityCow> implements GEntityCow {

    public GEntityCowImpl(World world) {
        super(new EntityCow(EntityTypes.COW, world));
    }
}
