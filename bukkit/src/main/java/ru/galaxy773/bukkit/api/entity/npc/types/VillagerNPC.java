package ru.galaxy773.bukkit.api.entity.npc.types;

import ru.galaxy773.bukkit.api.entity.npc.NPC;

public interface VillagerNPC extends NPC {

    Profession getProfession();

    void setVillagerProfession(Profession profession);

    enum Profession {
        NORMAL,
        FARMER,
        LIBRARIAN,
        PRIEST,
        BLACKSMITH,
        BUTCHER,
        NITWIT,
        HUSK
    }
}
