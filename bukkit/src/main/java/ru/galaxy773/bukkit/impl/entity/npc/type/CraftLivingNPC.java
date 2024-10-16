package ru.galaxy773.bukkit.impl.entity.npc.type;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import ru.galaxy773.bukkit.impl.entity.npc.CraftNPC;
import ru.galaxy773.bukkit.impl.entity.npc.NPCManager;

public abstract class CraftLivingNPC extends CraftNPC {

	
    protected CraftLivingNPC(JavaPlugin javaPlugin, NPCManager npcManager, Location location) {
        super(javaPlugin, npcManager, location);
    }

    @Override
    public void spawnEntity(Player player) {
        if (entity == null)
            return;

        PACKET_CONTAINER.getSpawnEntityLivingPacket(entity).sendPacket(player);
    }

    void sendPacketMetaData() {
        sendNearby(PACKET_CONTAINER.getEntityMetadataPacket(entity));
    }
}
