package ru.galaxy773.bukkit.nms.interfaces.entity;

import ru.galaxy773.bukkit.api.entity.npc.types.VillagerNPC;

public interface GEntityVillager extends GEntityLiving {

    VillagerNPC.Profession getProfession();

    void setVillagerProfession(VillagerNPC.Profession profession);
}
