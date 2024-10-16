package ru.galaxy773.bukkit.nms.v1_16_R3.entity;

import net.minecraft.server.v1_16_R3.EntitySlime;
import net.minecraft.server.v1_16_R3.EntityTypes;
import net.minecraft.server.v1_16_R3.World;
import ru.galaxy773.bukkit.nms.interfaces.entity.GEntitySlime;

public class GEntitySlimeImpl extends GEntityLivingBase<EntitySlime> implements GEntitySlime {

    public GEntitySlimeImpl(World world) {
        super(new EntitySlime(EntityTypes.SLIME, world));
    }

    @Override
    public int getSize() {
        return entity.getSize();
    }

    @Override
    public void setSize(int size) {
        entity.setSize(size, true);
    }

    @Override
    public void setInvisible(boolean invisible) {

    }
}
