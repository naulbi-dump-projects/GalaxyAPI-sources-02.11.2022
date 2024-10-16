package ru.galaxy773.bukkit.nms.v1_16_R3.entity;

import net.minecraft.server.v1_16_R3.EntityLiving;
import net.minecraft.server.v1_16_R3.EntityTypes;
import net.minecraft.server.v1_16_R3.IRegistry;
import net.minecraft.server.v1_16_R3.RegistryBlocks;
import ru.galaxy773.bukkit.nms.interfaces.entity.GEntityLiving;
import ru.galaxy773.bukkit.nms.v1_16_R3.entity.util.CustomEntityRegistry;
import ru.galaxy773.multiplatform.api.utils.reflection.ReflectionUtil;

public abstract class GEntityLivingBase<T extends EntityLiving> extends GEntityBase<T> implements GEntityLiving {

    private static final CustomEntityRegistry CUSTOM_ENTITY_REGISTRY =
            new CustomEntityRegistry((RegistryBlocks<EntityTypes<?>>) ReflectionUtil.getStaticValue(IRegistry.class, "ENTITY_TYPE"));

    protected GEntityLivingBase(T entity) {
        super(entity);
    }

    public static CustomEntityRegistry getCustomEntityRegistry() {
        return GEntityLivingBase.CUSTOM_ENTITY_REGISTRY;
    }

    @Override
    public T getEntityNms() {
        return super.getEntityNms();
    }

    @Override
    public float getHeadPitch() {
        return entity.am;
    }

    @Override
    public void setCollides(boolean collides) {
        entity.collides = collides;
    }
}
