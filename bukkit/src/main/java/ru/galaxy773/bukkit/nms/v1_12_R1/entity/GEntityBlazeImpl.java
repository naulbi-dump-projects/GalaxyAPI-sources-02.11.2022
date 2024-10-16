package ru.galaxy773.bukkit.nms.v1_12_R1.entity;

import net.minecraft.server.v1_12_R1.EntityBlaze;
import net.minecraft.server.v1_12_R1.World;
import ru.galaxy773.bukkit.nms.interfaces.entity.GEntityBlaze;

public class GEntityBlazeImpl extends GEntityLivingBase<EntityBlaze> implements GEntityBlaze {

    public GEntityBlazeImpl(World world) {
        super(new EntityBlaze(world));
    }
}
