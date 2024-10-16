package ru.galaxy773.bukkit.nms.interfaces.entity;

import ru.galaxy773.bukkit.api.entity.npc.types.EnderDragonNPC;

public interface GEntityDragon extends GEntityLiving {

    EnderDragonNPC.Phase getPhase();

    void setPhase(EnderDragonNPC.Phase phase);
}
