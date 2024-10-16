package ru.galaxy773.bukkit.nms.v1_16_R3.entity;

import net.minecraft.server.v1_16_R3.EntityZombie;
import net.minecraft.server.v1_16_R3.World;
import ru.galaxy773.bukkit.nms.interfaces.entity.GEntityZombie;

public class GEntityZombieImpl extends GEntityLivingBase<EntityZombie> implements GEntityZombie {

    public GEntityZombieImpl(World world) {
        super(new EntityZombie(world));
    }

    @Override
    public boolean isBaby() {
        return entity.isBaby();
    }

    @Override
    public void setBaby(boolean baby) {
        entity.setBaby(baby);
    }
}