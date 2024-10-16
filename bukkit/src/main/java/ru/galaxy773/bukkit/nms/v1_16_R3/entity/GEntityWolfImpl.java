package ru.galaxy773.bukkit.nms.v1_16_R3.entity;

import net.minecraft.server.v1_16_R3.EntityTypes;
import net.minecraft.server.v1_16_R3.EntityWolf;
import net.minecraft.server.v1_16_R3.EnumColor;
import net.minecraft.server.v1_16_R3.World;
import org.bukkit.DyeColor;
import ru.galaxy773.bukkit.nms.interfaces.entity.GEntityWolf;

public class GEntityWolfImpl extends GEntityLivingBase<EntityWolf> implements GEntityWolf {

    public GEntityWolfImpl(World world) {
        super(new EntityWolf(EntityTypes.WOLF, world));
    }

    @Override
    public DyeColor getCollarColor() {
        return DyeColor.getByWoolData((byte)entity.getCollarColor().getColorIndex());
    }

    @Override
    public void setCollarColor(DyeColor color) {
        entity.setTamed(true);
        entity.setCollarColor(EnumColor.fromColorIndex(color.getWoolData()));
    }

    @Override
    public boolean isAngry() {
        return entity.isAngry();
    }

    @Override
    public void setAngry(boolean angry) {
    }

    @Override
    public boolean isSitting() {
        return entity.isSitting();
    }

    @Override
    public void setSitting(boolean sitting) {
        entity.setSitting(sitting);
    }
}
