package ru.galaxy773.bukkit.impl.entity.npc.type;

import org.bukkit.Location;
import org.bukkit.plugin.java.JavaPlugin;
import ru.galaxy773.bukkit.api.entity.npc.NpcType;
import ru.galaxy773.bukkit.api.entity.npc.types.MushroomCowNPC;
import ru.galaxy773.bukkit.impl.entity.npc.NPCManager;
import ru.galaxy773.bukkit.nms.interfaces.entity.GEntityLiving;
import ru.galaxy773.bukkit.nms.interfaces.entity.GEntityMushroomCow;

public class CraftMushroomCowNPC extends CraftLivingNPC implements MushroomCowNPC {

    public CraftMushroomCowNPC(JavaPlugin javaPlugin, NPCManager npcManager, Location location) {
        super(javaPlugin, npcManager, location);
    }

    @Override
    public GEntityLiving createNMSEntity() {
        return NMS_MANAGER.createGEntity(GEntityMushroomCow.class, location);
    }

    @Override
    public void setInvisible(boolean invisible) {
    	((GEntityMushroomCow)entity).setInvisible(invisible);
        sendPacketMetaData();
    }
    
    @Override
    public NpcType getType() {
        return NpcType.MUSHROOM_COW;
    }
}
