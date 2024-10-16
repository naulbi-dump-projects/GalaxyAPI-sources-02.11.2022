package ru.galaxy773.bukkit.nms.interfaces.entity;

public interface GEntitySlime extends GEntityLiving {

    int getSize();

    void setSize(int size);
    
    void setInvisible(boolean invisible);
}
