package ru.galaxy773.bukkit.nms.v1_16_R3.entity;

import net.minecraft.server.v1_16_R3.*;
import ru.galaxy773.bukkit.api.entity.npc.types.VillagerNPC;
import ru.galaxy773.bukkit.nms.interfaces.entity.GEntityVillager;

public class GEntityVillagerImpl extends GEntityLivingBase<EntityVillager> implements GEntityVillager {

    public GEntityVillagerImpl(World world) {
        super(new EntityVillager(EntityTypes.VILLAGER, world));
    }

    @Override
    public VillagerNPC.Profession getProfession() {
        return VillagerNPC.Profession.values()[entity.getVillagerData().getLevel() + 1];
    }

    @Override
    public void setVillagerProfession(VillagerNPC.Profession profession) {
        final VillagerData villagerData = new VillagerData((entity).getVillagerData().getType(), VillagerProfession.class.getEnumConstants()[profession.ordinal() - 1], profession.ordinal() - 1);
        entity.setVillagerData(villagerData);
    }
}
