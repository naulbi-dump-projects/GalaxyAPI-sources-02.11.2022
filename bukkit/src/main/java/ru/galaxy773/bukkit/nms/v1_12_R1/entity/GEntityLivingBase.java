package ru.galaxy773.bukkit.nms.v1_12_R1.entity;

import net.minecraft.server.v1_12_R1.EntityLiving;
import ru.galaxy773.bukkit.nms.interfaces.entity.GEntityLiving;

public abstract class GEntityLivingBase<T extends EntityLiving> extends GEntityBase<T> implements GEntityLiving {

    protected GEntityLivingBase(T entity) {
        super(entity);
    }

    @Override
    public T getEntityNms() {
        return super.getEntityNms();
    }

    @Override
    public float getHeadPitch() {
        return entity.aP;
    }

    @Override
    public void setCollides(boolean collides) {
        entity.collides = collides;
    }
}
