package ru.galaxy773.bukkit.impl.entity.npc.type;

import org.bukkit.Location;
import org.bukkit.plugin.java.JavaPlugin;
import ru.galaxy773.bukkit.api.entity.npc.NpcType;
import ru.galaxy773.bukkit.api.entity.npc.types.SlimeNPC;
import ru.galaxy773.bukkit.impl.entity.npc.NPCManager;
import ru.galaxy773.bukkit.nms.interfaces.entity.GEntityLiving;
import ru.galaxy773.bukkit.nms.interfaces.entity.GEntitySlime;

public class CraftSlimeNPC extends CraftLivingNPC implements SlimeNPC {

    public CraftSlimeNPC(JavaPlugin javaPlugin, NPCManager npcManager, Location location) {
        super(javaPlugin, npcManager, location);
    }

    @Override
    public GEntityLiving createNMSEntity() {
        GEntitySlime slime =  NMS_MANAGER.createGEntity(GEntitySlime.class, location);
        slime.setSize(1);
        return slime;
    }

    @Override
    public int getSize() {
        return ((GEntitySlime)entity).getSize();
    }

    @Override
    public void setSize(int size) {
        ((GEntitySlime)entity).setSize(size);
        sendPacketMetaData();
    }

    @Override
    public NpcType getType() {
        return NpcType.SLIME;
    }
    
    @Override
    public void setInvisible(boolean invisible) {
        ((GEntitySlime)entity).setInvisible(invisible);
        sendPacketMetaData();
    }
}
