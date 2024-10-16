package ru.galaxy773.bukkit.impl.listeners.lobby;

import com.google.common.collect.ImmutableSet;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.block.*;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.hanging.HangingBreakByEntityEvent;
import org.bukkit.event.player.*;
import org.bukkit.plugin.java.JavaPlugin;
import ru.galaxy773.bukkit.api.gamer.BukkitGamer;
import ru.galaxy773.bukkit.api.gamer.GamerManager;
import ru.galaxy773.bukkit.api.utils.bukkit.WorldTime;
import ru.galaxy773.bukkit.api.utils.listener.GListener;
import ru.galaxy773.multiplatform.api.locale.Lang;

@SuppressWarnings("deprecation")
public class LobbyGuardListener extends GListener<JavaPlugin> {

    private final ImmutableSet<Material> blockedInteracts = ImmutableSet.of(
            Material.ACACIA_DOOR, Material.BIRCH_DOOR, Material.DARK_OAK_DOOR,
            Material.IRON_DOOR, Material.JUNGLE_DOOR, Material.SPRUCE_DOOR,
            Material.TRAP_DOOR, Material.TRAP_DOOR, Material.WOOD_DOOR,
            Material.WOODEN_DOOR, Material.TRAP_DOOR, Material.ITEM_FRAME
    );

	public LobbyGuardListener(JavaPlugin javaPlugin, World world, int time) {
		super(javaPlugin);
	    WorldTime.freeze(WorldTime.getTimeBuilder()
                .world(world)
                .time(time)
                .storm(false)
                .build());
		Bukkit.getScheduler().runTaskTimer(javaPlugin, new WorldTime.TimeTask(), 5, 100);
	}

	@EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onHangingBreak(HangingBreakByEntityEvent event) {
	    if (event.getEntity().getType() == EntityType.ITEM_FRAME)
            event.setCancelled(true);
    }

	@EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
	public void onEntityDamage(EntityDamageEvent e) {
		e.setCancelled(true);
	}

	@EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
	public void onBlockPhysics(BlockPhysicsEvent e) {
		e.setCancelled(true);
	}

	@EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onLeavesDecay(LeavesDecayEvent e) {
	    e.setCancelled(true);
    }

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onBlockSpread(BlockSpreadEvent e) {
	    e.setCancelled(true);
    }

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onBlockBreak(BlockBreakEvent e) {
        e.setCancelled(true);
    }

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onBlockPlace(BlockPlaceEvent e) {
        e.setCancelled(true);
    }

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onFoodChange(FoodLevelChangeEvent e) {
        e.setCancelled(true);
    }

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onEntityExplode(EntityExplodeEvent e) {
        e.setCancelled(true);
    }

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onDrop(PlayerDropItemEvent e) {
        e.setCancelled(true);
    }

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onBlockExplode(BlockExplodeEvent e) {
        e.setCancelled(true);
    }

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onPickup(PlayerPickupItemEvent e) {
        e.setCancelled(true);
    }

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onPlayerFrame(PlayerInteractEntityEvent e){
        e.setCancelled(true);
    }

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onArmorInteract(PlayerArmorStandManipulateEvent e) {
        e.setCancelled(true);
    }

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onFade(BlockFadeEvent event) {
	    event.setCancelled(true);
    }

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onForm(BlockFormEvent event) {
        event.setCancelled(true);
    }

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onGrow(BlockGrowEvent event ) {
	    event.setCancelled(true);
    }

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onInteract(PlayerInteractEvent e) {
        if (blockedInteracts.contains(e.getClickedBlock().getType())) {
            e.setCancelled(true);
        }
    }

    @EventHandler(priority = EventPriority.NORMAL)
    private void onAsyncChat(AsyncPlayerChatEvent e) {
        BukkitGamer gamer = GamerManager.getGamer(e.getPlayer());
        e.setFormat(Lang.getMessage("LOBBY_CHAT_FORMAT").replace("{message}", "%2$s").replace("{player}", "%1$s").replace("{prefix}", gamer.getChatPrefix()).replace("{level}", String.valueOf(gamer.getLevel())));
    }
}
