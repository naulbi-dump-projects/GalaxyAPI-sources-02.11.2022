package ru.galaxy773.bukkit.nms.v1_16_R3.entity;

import net.minecraft.server.v1_16_R3.EntityMushroomCow;
import net.minecraft.server.v1_16_R3.EntityTypes;
import net.minecraft.server.v1_16_R3.World;
import ru.galaxy773.bukkit.nms.interfaces.entity.GEntityMushroomCow;

public class GEntityMushroomCowImpl extends GEntityLivingBase<EntityMushroomCow> implements GEntityMushroomCow {

    public GEntityMushroomCowImpl(World world) {
        super(new EntityMushroomCow(EntityTypes.MOOSHROOM, world));
    }

    @Override
    public void setInvisible(boolean invisible) {

    }
}

