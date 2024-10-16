package ru.galaxy773.bukkit.impl.entity.npc.type;

import org.bukkit.Location;
import org.bukkit.plugin.java.JavaPlugin;
import ru.galaxy773.bukkit.api.entity.npc.NpcType;
import ru.galaxy773.bukkit.api.entity.npc.types.BlazeNPC;
import ru.galaxy773.bukkit.impl.entity.npc.NPCManager;
import ru.galaxy773.bukkit.nms.interfaces.entity.GEntityBlaze;
import ru.galaxy773.bukkit.nms.interfaces.entity.GEntityLiving;

public class CraftBlazeNPC extends CraftLivingNPC implements BlazeNPC {

    public CraftBlazeNPC(JavaPlugin javaPlugin, NPCManager npcManager, Location location) {
        super(javaPlugin, npcManager, location);
    }

    @Override
    public GEntityLiving createNMSEntity() {
        return NMS_MANAGER.createGEntity(GEntityBlaze.class, location);
    }

    @Override
    public NpcType getType() {
        return NpcType.BLAZE;
    }
}
