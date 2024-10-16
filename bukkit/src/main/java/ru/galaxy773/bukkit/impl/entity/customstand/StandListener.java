package ru.galaxy773.bukkit.impl.entity.customstand;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.player.PlayerRespawnEvent;
import ru.galaxy773.bukkit.api.BukkitPlugin;
import ru.galaxy773.bukkit.api.entity.stand.CustomStand;
import ru.galaxy773.bukkit.api.event.player.AsyncPlayerInUseEntityEvent;
import ru.galaxy773.bukkit.api.event.player.PlayerInteractCustomStandEvent;
import ru.galaxy773.bukkit.api.utils.bukkit.BukkitUtil;
import ru.galaxy773.bukkit.api.utils.listener.GListener;
import ru.galaxy773.bukkit.impl.entity.EntityAPIImpl;

public class StandListener extends GListener<BukkitPlugin> {

	private final StandManager standManager;

    public StandListener(EntityAPIImpl entityAPI) {
    	super(BukkitPlugin.getInstance());
        this.standManager = entityAPI.getStandManager();
    }

    @EventHandler
    public void onInteract(AsyncPlayerInUseEntityEvent e) {
        Player player = e.getPlayer();
        int entityId = e.getEntityId();

        CustomStand stand = standManager.getStand(entityId);
        if (stand == null) {
            return;
        }
        
        PlayerInteractCustomStandEvent event = new PlayerInteractCustomStandEvent(player, stand,
                (e.getAction() == AsyncPlayerInUseEntityEvent.Action.ATTACK
                        ? PlayerInteractCustomStandEvent.Action.LEFT_CLICK
                        : PlayerInteractCustomStandEvent.Action.RIGHT_CLICK));
        BukkitUtil.runTask(() -> BukkitUtil.callEvent(event));
    }

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onPlayerRespawn(PlayerRespawnEvent e) {
        Player player = e.getPlayer();
        standManager.getStands().values().forEach(stand -> stand.destroy(player));
    }
}
