package ru.galaxy773.bukkit.impl.entity.npc;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerQuitEvent;
import ru.galaxy773.bukkit.api.BukkitPlugin;
import ru.galaxy773.bukkit.api.entity.depend.ClickType;
import ru.galaxy773.bukkit.api.entity.npc.NPC;
import ru.galaxy773.bukkit.api.event.player.AsyncPlayerInUseEntityEvent;
import ru.galaxy773.bukkit.api.event.player.PlayerInteractNPCEvent;
import ru.galaxy773.multiplatform.api.utils.cooldown.Cooldown;
import ru.galaxy773.bukkit.api.utils.bukkit.BukkitUtil;
import ru.galaxy773.bukkit.api.utils.listener.GListener;
import ru.galaxy773.bukkit.impl.entity.EntityAPIImpl;

public class NPCListener extends GListener<BukkitPlugin> {

    private final NPCManager npcManager;

    public NPCListener(EntityAPIImpl entityAPI) {
        super(BukkitPlugin.getInstance());
        this.npcManager = entityAPI.getNpcManager();
    }

    @EventHandler
    public void onInterractNPC(AsyncPlayerInUseEntityEvent e) {
        Player player = e.getPlayer();
        int entityId = e.getEntityId();

        NPC npc = npcManager.getNPCs().get(entityId);
        if (npc == null) {
            return;
        }
        if (Cooldown.hasCooldown(player.getName(), "npcUse")) {
            return;
        }
        Cooldown.addCooldown(player.getName(), "npcUse", 10L);
        PlayerInteractNPCEvent event = new PlayerInteractNPCEvent(player, npc,
                (e.getAction() == AsyncPlayerInUseEntityEvent.Action.ATTACK
                        ? ClickType.LEFT
                        : ClickType.RIGHT));
        BukkitUtil.runTask(() -> BukkitUtil.callEvent(event));
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent e) {
        Player player = e.getPlayer();
        npcManager.removeFromTeams(player);
    }
}
