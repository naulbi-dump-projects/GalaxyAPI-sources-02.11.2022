package ru.galaxy773.bukkit.nms.v1_12_R1.entity;

import net.minecraft.server.v1_12_R1.EntityCreeper;
import net.minecraft.server.v1_12_R1.World;
import ru.galaxy773.bukkit.nms.interfaces.entity.GEntityCreeper;

public class GEntityCreeperImpl extends GEntityLivingBase<EntityCreeper> implements GEntityCreeper {

    public GEntityCreeperImpl(World world) {
        super(new EntityCreeper(world));
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
