package ru.galaxy773.bukkit.nms.v1_12_R1.entity;

import net.minecraft.server.v1_12_R1.EntityCow;
import net.minecraft.server.v1_12_R1.World;
import ru.galaxy773.bukkit.nms.interfaces.entity.GEntityCow;

public class GEntityCowImpl extends GEntityLivingBase<EntityCow> implements GEntityCow {

    public GEntityCowImpl(World world) {
        super(new EntityCow(world));
    }
}
