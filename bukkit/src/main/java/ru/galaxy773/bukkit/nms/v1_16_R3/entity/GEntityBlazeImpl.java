package ru.galaxy773.bukkit.nms.v1_16_R3.entity;

import net.minecraft.server.v1_16_R3.EntityBlaze;
import net.minecraft.server.v1_16_R3.EntityTypes;
import net.minecraft.server.v1_16_R3.World;
import ru.galaxy773.bukkit.nms.interfaces.entity.GEntityBlaze;

public class GEntityBlazeImpl extends GEntityLivingBase<EntityBlaze> implements GEntityBlaze {

    public GEntityBlazeImpl(World world) {
        super(new EntityBlaze(EntityTypes.BLAZE, world));
    }
}
