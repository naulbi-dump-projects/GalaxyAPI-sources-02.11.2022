package ru.galaxy773.bukkit.nms.interfaces.entity;

import org.bukkit.DyeColor;

public interface GEntityWolf extends GEntityLiving {

    DyeColor getCollarColor();

    void setCollarColor(DyeColor color);

    boolean isAngry();

    void setAngry(boolean angry);

    boolean isSitting();

    void setSitting(boolean sitting);
}
