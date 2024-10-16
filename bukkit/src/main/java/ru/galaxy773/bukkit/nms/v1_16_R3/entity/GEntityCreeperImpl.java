package ru.galaxy773.bukkit.nms.v1_16_R3.entity;

import net.minecraft.server.v1_16_R3.EntityCreeper;
import net.minecraft.server.v1_16_R3.EntityTypes;
import net.minecraft.server.v1_16_R3.World;
import ru.galaxy773.bukkit.nms.interfaces.entity.GEntityCreeper;

public class GEntityCreeperImpl extends GEntityLivingBase<EntityCreeper> implements GEntityCreeper {

    public GEntityCreeperImpl(World world) {
        super(new EntityCreeper(EntityTypes.CREEPER, world));
    }

    @Override
    public boolean isPowered() {
        return entity.isPowered();
    }

    @Override
    public void setPowered(boolean powered) {
        entity.setPowered(powered);
    }
}
