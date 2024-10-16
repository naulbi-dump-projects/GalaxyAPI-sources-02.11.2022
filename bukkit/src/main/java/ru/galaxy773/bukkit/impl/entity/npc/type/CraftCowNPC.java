package ru.galaxy773.bukkit.impl.entity.npc.type;

import org.bukkit.Location;
import org.bukkit.plugin.java.JavaPlugin;
import ru.galaxy773.bukkit.api.entity.npc.NpcType;
import ru.galaxy773.bukkit.api.entity.npc.types.CowNPC;
import ru.galaxy773.bukkit.impl.entity.npc.NPCManager;
import ru.galaxy773.bukkit.nms.interfaces.entity.GEntityCow;
import ru.galaxy773.bukkit.nms.interfaces.entity.GEntityLiving;

public class CraftCowNPC extends CraftLivingNPC implements CowNPC {

    public CraftCowNPC(JavaPlugin javaPlugin, NPCManager npcManager, Location location) {
        super(javaPlugin, npcManager, location);
    }

    @Override
    public GEntityLiving createNMSEntity() {
        return NMS_MANAGER.createGEntity(GEntityCow.class, location);
    }

    @Override
    public NpcType getType() {
        return NpcType.COW;
    }
}
