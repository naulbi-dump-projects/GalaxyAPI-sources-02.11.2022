package ru.galaxy773.bukkit.api.entity.npc.types;

import ru.galaxy773.bukkit.api.entity.npc.NPC;

public interface CreeperNPC extends NPC {
    
    boolean isPowered();

    void setPowered(boolean flag);
}
