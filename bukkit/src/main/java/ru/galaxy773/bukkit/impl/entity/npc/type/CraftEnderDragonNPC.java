package ru.galaxy773.bukkit.impl.entity.npc.type;

import org.bukkit.Location;
import org.bukkit.plugin.java.JavaPlugin;
import ru.galaxy773.bukkit.api.entity.npc.NpcType;
import ru.galaxy773.bukkit.api.entity.npc.types.EnderDragonNPC;
import ru.galaxy773.bukkit.impl.entity.npc.NPCManager;
import ru.galaxy773.bukkit.nms.interfaces.entity.GEntityDragon;
import ru.galaxy773.bukkit.nms.interfaces.entity.GEntityLiving;

public class CraftEnderDragonNPC extends CraftLivingNPC implements EnderDragonNPC {

    public CraftEnderDragonNPC(JavaPlugin javaPlugin, NPCManager npcManager, Location location) {
        super(javaPlugin, npcManager, location);
    }

    @Override
    public GEntityLiving createNMSEntity() {
        return NMS_MANAGER.createGEntity(GEntityDragon.class, location);
    }

    @Override
    public NpcType getType() {
        return NpcType.ENDER_DRAGON;
    }

    @Override
    public Phase getPhase() {
        return ((GEntityDragon)entity).getPhase();
    }

    @Override
    public void setPhase(Phase phase) {
        ((GEntityDragon)entity).setPhase(phase);
        sendPacketMetaData();
    }
}
