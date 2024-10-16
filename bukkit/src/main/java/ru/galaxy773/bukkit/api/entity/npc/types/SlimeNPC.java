package ru.galaxy773.bukkit.api.entity.npc.types;

import ru.galaxy773.bukkit.api.entity.npc.NPC;

public interface SlimeNPC extends NPC {

    int getSize();

    void setSize(int size);
    
    void setInvisible(boolean invisible);
}
