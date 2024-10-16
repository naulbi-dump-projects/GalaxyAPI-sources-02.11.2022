package ru.galaxy773.bukkit.api.entity.npc.types;

import ru.galaxy773.bukkit.api.entity.npc.NPC;

public interface ZombieNPC extends NPC {
	
    boolean isBaby();

    void setBaby(boolean baby);
}
