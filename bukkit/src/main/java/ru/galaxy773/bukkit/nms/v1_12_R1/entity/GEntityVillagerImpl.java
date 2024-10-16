package ru.galaxy773.bukkit.nms.v1_12_R1.entity;

import net.minecraft.server.v1_12_R1.EntityVillager;
import net.minecraft.server.v1_12_R1.World;
import ru.galaxy773.bukkit.api.entity.npc.types.VillagerNPC;
import ru.galaxy773.bukkit.nms.interfaces.entity.GEntityVillager;

public class GEntityVillagerImpl extends GEntityLivingBase<EntityVillager> implements GEntityVillager {

    public GEntityVillagerImpl(World world) {
        super(new EntityVillager(world));
    }

    @Override
    public VillagerNPC.Profession getProfession() {
        return VillagerNPC.Profession.values()[entity.getProfession() + 1];
    }

    @Override
    public void setVillagerProfession(VillagerNPC.Profession profession) {
        entity.setProfession(profession.ordinal() - 1);
    }
}
