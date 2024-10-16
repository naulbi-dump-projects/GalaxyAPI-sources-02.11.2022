package ru.galaxy773.bukkit.impl.entity.npc.type;

import org.bukkit.Location;
import org.bukkit.plugin.java.JavaPlugin;
import ru.galaxy773.bukkit.api.entity.npc.NpcType;
import ru.galaxy773.bukkit.api.entity.npc.types.VillagerNPC;
import ru.galaxy773.bukkit.impl.entity.npc.NPCManager;
import ru.galaxy773.bukkit.nms.interfaces.entity.GEntityLiving;
import ru.galaxy773.bukkit.nms.interfaces.entity.GEntityVillager;

public class CraftVillagerNPC extends CraftLivingNPC implements VillagerNPC {

    public CraftVillagerNPC(JavaPlugin javaPlugin, NPCManager npcManager, Location location) {
        super(javaPlugin, npcManager, location);
    }

    @Override
    public GEntityLiving createNMSEntity() {
        GEntityVillager villager = NMS_MANAGER.createGEntity(GEntityVillager.class, location);
        villager.setVillagerProfession(Profession.FARMER);
        return villager;
    }

    @Override
    public Profession getProfession() {
        return ((GEntityVillager)entity).getProfession();
    }

    @Override
    public void setVillagerProfession(Profession profession) {
        ((GEntityVillager)entity).setVillagerProfession(profession);
        sendPacketMetaData();
    }

    @Override
    public NpcType getType() {
        return NpcType.VILLAGER;
    }
}
