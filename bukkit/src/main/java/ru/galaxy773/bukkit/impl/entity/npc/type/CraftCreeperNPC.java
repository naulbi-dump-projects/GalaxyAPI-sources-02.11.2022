package ru.galaxy773.bukkit.impl.entity.npc.type;

import org.bukkit.Location;
import org.bukkit.plugin.java.JavaPlugin;
import ru.galaxy773.bukkit.api.entity.npc.NpcType;
import ru.galaxy773.bukkit.api.entity.npc.types.CreeperNPC;
import ru.galaxy773.bukkit.impl.entity.npc.NPCManager;
import ru.galaxy773.bukkit.nms.interfaces.entity.GEntityCreeper;
import ru.galaxy773.bukkit.nms.interfaces.entity.GEntityLiving;

public class CraftCreeperNPC extends CraftLivingNPC implements CreeperNPC {

    public CraftCreeperNPC(JavaPlugin javaPlugin, NPCManager npcManager, Location location) {
        super(javaPlugin, npcManager, location);
    }

    @Override
    public GEntityLiving createNMSEntity() {
        return NMS_MANAGER.createGEntity(GEntityCreeper.class, location);
    }


    @Override
    public boolean isPowered() {
        return ((GEntityCreeper)entity).isPowered();
    }

    @Override
    public void setPowered(boolean flag) {
        if (this.isPowered() == flag)
            return;

        ((GEntityCreeper)entity).setPowered(flag);
        sendPacketMetaData();
    }

    @Override
    public NpcType getType() {
        return NpcType.CREEPER;
    }
}
