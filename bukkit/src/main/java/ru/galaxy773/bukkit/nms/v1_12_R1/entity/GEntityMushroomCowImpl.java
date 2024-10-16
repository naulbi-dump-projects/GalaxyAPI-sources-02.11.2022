package ru.galaxy773.bukkit.nms.v1_12_R1.entity;

import net.minecraft.server.v1_12_R1.EntityMushroomCow;
import net.minecraft.server.v1_12_R1.World;
import ru.galaxy773.bukkit.nms.interfaces.entity.GEntityMushroomCow;

public class GEntityMushroomCowImpl extends GEntityLivingBase<EntityMushroomCow> implements GEntityMushroomCow {

    public GEntityMushroomCowImpl(World world) {
        super(new EntityMushroomCow(world));
    }
    
    @Override
    public void setInvisible(boolean invisible) {
    	entity.setInvisible(invisible);
    }
}

