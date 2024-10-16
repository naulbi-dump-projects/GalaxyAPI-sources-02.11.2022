package ru.galaxy773.bukkit.impl.entity.npc.type;

import org.bukkit.Location;
import org.bukkit.plugin.java.JavaPlugin;
import ru.galaxy773.bukkit.api.entity.npc.NpcType;
import ru.galaxy773.bukkit.api.entity.npc.types.ZombieNPC;
import ru.galaxy773.bukkit.impl.entity.npc.NPCManager;
import ru.galaxy773.bukkit.nms.interfaces.entity.GEntityLiving;
import ru.galaxy773.bukkit.nms.interfaces.entity.GEntityZombie;

public class CraftZombieNPC extends CraftLivingNPC implements ZombieNPC {

    public CraftZombieNPC(JavaPlugin javaPlugin, NPCManager npcManager, Location location) {
        super(javaPlugin, npcManager, location);
    }

    @Override
    public GEntityLiving createNMSEntity() {
        return NMS_MANAGER.createGEntity(GEntityZombie.class, location);
    }

    @Override
    public NpcType getType() {
        return NpcType.ZOMBIE;
    }

    @Override
    public boolean isBaby() {
        return ((GEntityZombie)entity).isBaby();
    }

    @Override
    public void setBaby(boolean baby) {
        ((GEntityZombie)entity).setBaby(baby);
        sendPacketMetaData();
    }
}
