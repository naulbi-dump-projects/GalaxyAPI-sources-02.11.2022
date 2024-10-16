package ru.galaxy773.lobby.listeners;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.spigotmc.event.player.PlayerSpawnLocationEvent;
import ru.galaxy773.bukkit.api.entity.depend.ClickType;
import ru.galaxy773.bukkit.api.event.gamer.GamerChangeSettingEvent;
import ru.galaxy773.bukkit.api.event.gamer.async.AsyncGamerJoinEvent;
import ru.galaxy773.bukkit.api.event.player.PlayerInteractCustomStandEvent;
import ru.galaxy773.bukkit.api.event.player.PlayerInteractHologramEvent;
import ru.galaxy773.bukkit.api.event.player.PlayerInteractNPCEvent;
import ru.galaxy773.bukkit.api.gamer.BukkitGamer;
import ru.galaxy773.bukkit.api.gamer.GamerManager;
import ru.galaxy773.bukkit.api.utils.bukkit.BukkitUtil;
import ru.galaxy773.bukkit.api.utils.listener.GListener;
import ru.galaxy773.lobby.Lobby;
import ru.galaxy773.lobby.config.GameConfig;
import ru.galaxy773.lobby.utils.CommandUtil;
import ru.galaxy773.multiplatform.api.gamer.constants.SettingsType;
import ru.galaxy773.multiplatform.api.locale.Lang;
import ru.galaxy773.multiplatform.api.utils.cooldown.Cooldown;

public class LobbyListener extends GListener<Lobby> {

    private final GameConfig gameConfig;

    public LobbyListener(Lobby plugin, GameConfig gameConfig) {
        super(plugin);
        this.gameConfig = gameConfig;
    }

    @EventHandler(priority=EventPriority.HIGH)
    private void onAsyncJoin(AsyncGamerJoinEvent event) {
        BukkitGamer gamer = event.getGamer();
        gamer.sendTitle(Lang.getMessage("LOBBY_JOIN_TITLE"), Lang.getMessage("LOBBY_JOIN_SUBTITLE"), 10, 50, 10);
        gamer.sendMessageLocale("LOBBY_JOIN_MESSAGE");
    }

    @EventHandler(priority=EventPriority.HIGHEST)
    private void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        BukkitGamer gamer = GamerManager.getGamer(player);
        player.teleport(this.gameConfig.getSpawn());

        BukkitUtil.runTaskLaterAsync(10, () -> player.setCompassTarget(this.gameConfig.getSpawn()));

        if (gamer.getSetting(SettingsType.FLY) && gamer.hasChildGroup(SettingsType.FLY.getGroup()))
            player.setAllowFlight(true);

        if (!gamer.getSetting(SettingsType.CHAT))
            gamer.sendMessageLocale("SETTING_CHAT_IS_DISABLED");
    }

    @EventHandler
    private void onHologram(PlayerInteractHologramEvent event) {
        String command = this.gameConfig.getHologramCommand(event.getHologram());
        if (command != null && event.getAction() == PlayerInteractCustomStandEvent.Action.RIGHT_CLICK) {
            if (Cooldown.hasCooldown(event.getPlayer().getName(), "HOLOGRAM_INTERACT")) {
                return;
            }
            CommandUtil.runCommand(event.getPlayer(), command);
            Cooldown.addCooldown(event.getPlayer().getName(), "HOLOGRAM_INTERACT", 10L);
        }
    }

    @EventHandler
    private void onNpc(PlayerInteractNPCEvent event) {
        String command = this.gameConfig.getNPCCommand(event.getNpc());
        if (command != null && event.getClickType() == ClickType.RIGHT) {
            if (Cooldown.hasCooldown(event.getPlayer().getName(), "NPC_INTERACT")) {
                return;
            }
            CommandUtil.runCommand(event.getPlayer(), command);
            Cooldown.addCooldown(event.getPlayer().getName(), "NPC_INTERACT", 10L);
        }
    }

    @EventHandler(priority=EventPriority.LOWEST)
    private void onSpawnSet(PlayerSpawnLocationEvent event) {
        event.setSpawnLocation(this.gameConfig.getSpawn());
    }

    @EventHandler(ignoreCancelled=true, priority=EventPriority.HIGHEST)
    private void onFall(PlayerMoveEvent e) {
        if (e.getTo().getBlockY() < this.gameConfig.getVoidSpawn()) {
            e.getPlayer().teleport(this.gameConfig.getSpawn());
            return;
        }

        Block block = e.getFrom().clone().add(0.0, -1.0, 0.0).getBlock();
        if (block.getType() == Material.EMERALD_BLOCK && this.gameConfig.getJumpPads().contains(block))
            e.getPlayer().setVelocity(e.getPlayer().getLocation().getDirection().multiply(2.5).setY(0.75));
    }

    @EventHandler(ignoreCancelled=true, priority=EventPriority.HIGHEST)
    private void onDropItem(PlayerDropItemEvent event) {
        event.setCancelled(true);
    }

    @EventHandler(ignoreCancelled=true, priority=EventPriority.HIGHEST)
    private void onPickupItem(PlayerPickupItemEvent event) {
        event.setCancelled(true);
    }

    @EventHandler(priority=EventPriority.HIGHEST, ignoreCancelled=true)
    public void onFly(GamerChangeSettingEvent event) {
        if (event.getSetting() != SettingsType.FLY)
            return;

        Player player = event.getGamer().getPlayer();
        if (event.isEnable()) {
            player.setAllowFlight(true);
            player.setFlying(true);
        } else {
            player.setFlying(false);
            player.setAllowFlight(false);
        }
    }
}

