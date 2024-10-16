package ru.galaxy773.lobby.parkour;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import ru.galaxy773.bukkit.api.event.gamer.GamerChangeSettingEvent;
import ru.galaxy773.bukkit.api.event.gamer.async.AsyncGamerJoinEvent;
import ru.galaxy773.bukkit.api.gamer.BukkitGamer;
import ru.galaxy773.bukkit.api.gamer.GamerManager;
import ru.galaxy773.bukkit.api.utils.listener.GListener;
import ru.galaxy773.lobby.Lobby;
import ru.galaxy773.lobby.api.parkour.ParkourManager;
import ru.galaxy773.lobby.customitems.CustomItem;
import ru.galaxy773.lobby.hider.HiderItem;
import ru.galaxy773.multiplatform.api.gamer.constants.SettingsType;

public class ParkourListener extends GListener<Lobby> {

    private final ParkourManager parkourManager;
    private final CustomItem backItem;
    private final CustomItem cancelItem;

    public ParkourListener(Lobby plugin, ParkourManager parkourManager) {
        super(plugin);
        this.parkourManager = parkourManager;
        this.backItem = new CustomItem(Material.SPECTRAL_ARROW, "PARKOUR_BACK_ITEM", (player, clickType, block) -> {
            ParkourWay playerParkour = this.parkourManager.getPlayerParkour(player);
            if (playerParkour == null) {
                return;
            }
            player.teleport((playerParkour.getCheckpoint(player) == null ? playerParkour.getStartLocation() : playerParkour.getCheckpoint(player).getLocation()).clone().add(0.5, 1.0, 0.5));
        });
        this.cancelItem = new CustomItem(Material.BARRIER, "PARKOUR_CANCEL_ITEM", (player, clickType, block) -> {
            ParkourWay playerParkour = this.parkourManager.getPlayerParkour(player);
            if (playerParkour == null) {
                return;
            }
            BukkitGamer gamer = GamerManager.getGamer(player);
            playerParkour.removePlayer(player);
            CustomItem.giveItems(player);
            CustomItem.giveProfileItem(player);
            HiderItem.giveToPlayer(player, gamer.getSetting(SettingsType.HIDER));
            gamer.sendMessageLocale("PARKOUR_FORCED_CANCEL");
        });
    }

    @EventHandler(priority=EventPriority.HIGHEST)
    private void onAsyncJoin(AsyncGamerJoinEvent event) {
        this.parkourManager.loadPlayer(event.getGamer().getPlayer());
    }

    @EventHandler(priority=EventPriority.HIGHEST)
    private void onQuit(PlayerQuitEvent event) {
        this.parkourManager.unloadPlayer(event.getPlayer());
    }

    @EventHandler(priority=EventPriority.LOWEST)
    private void onCheckpoint(PlayerInteractEvent event) {
        if (event.getAction() != Action.PHYSICAL) {
            return;
        }
        Player player = event.getPlayer();
        BukkitGamer gamer = GamerManager.getGamer((Player)player);
        if (event.getClickedBlock().getType().name().equals("GOLD_PLATE") || event.getClickedBlock().getType().name().equals("LEGACY_GOLD_PLATE")) {
            ParkourWay playerParkour = this.parkourManager.getPlayerParkour(player);
            if (playerParkour == null) {
                ParkourWay parkourWay = this.parkourManager.getParkourWay(event.getClickedBlock());
                if (parkourWay != null) {
                    parkourWay.addPlayer(player);
                    player.getInventory().clear();
                    this.backItem.givePlayer(player, 0);
                    this.cancelItem.givePlayer(player, 8);
                    HiderItem.giveToPlayer(player, gamer.getSetting(SettingsType.HIDER));
                    if (gamer.getSetting(SettingsType.FLY)) {
                        gamer.setSetting(SettingsType.FLY, false, false);
                    }
                    player.setFlying(false);
                    player.setAllowFlight(false);
                    gamer.sendMessageLocale("PARKOUR_START");
                }
                return;
            }
            if (playerParkour.getStartLocation().getBlock() == event.getClickedBlock()) {
                gamer.sendMessageLocale("PARKOUR_CANCEL_USE");
                return;
            }
            if (playerParkour.getEndLocation().getBlock().equals((Object)event.getClickedBlock())) {
                if (playerParkour.getCheckpoint(player) == null || playerParkour.getCheckpoint(player).getPosition() < playerParkour.getCheckpoints().size()) {
                    gamer.sendMessageLocale("PARKOUR_CHECKPOINTS_LACK");
                    return;
                }
                long bestTime = this.parkourManager.getPlayerBestTime(player, playerParkour);
                long time = playerParkour.getTime(player);
                if (bestTime == -1L || bestTime > time) {
                    this.parkourManager.setPlayerBestTime(player, playerParkour, time);
                }
                playerParkour.removePlayer(player);
                CustomItem.giveItems(player);
                CustomItem.giveProfileItem(player);
                HiderItem.giveToPlayer(player, gamer.getSetting(SettingsType.HIDER));
                if (bestTime == -1L) {
                    gamer.setGold(gamer.getGold() + playerParkour.getGoldReward(), true);
                    gamer.addExp(playerParkour.getExpReward());
                    gamer.sendMessageLocale("PARKOUR_END_WITH_REWARD", this.format(time), playerParkour.getGoldReward(), playerParkour.getExpReward());
                    return;
                }
                gamer.sendMessageLocale("PARKOUR_END", this.format(time));
                return;
            }
        }
        if (event.getClickedBlock().getType().name().equals("IRON_PLATE") || event.getClickedBlock().getType().name().equals("LEGACY_IRON_PLATE")) {
            ParkourWay parkourWay = this.parkourManager.getPlayerParkour(player);
            if (parkourWay == null) {
                return;
            }
            ParkourCheckpoint checkpoint = parkourWay.getCheckpoints().stream().filter(parkourCheckpoint -> parkourCheckpoint.getLocation().getBlock().equals((Object)event.getClickedBlock())).findFirst().orElse(null);
            ParkourCheckpoint playerCheckpoint = parkourWay.getCheckpoint(player);
            if (checkpoint == null || playerCheckpoint != null && playerCheckpoint.getPosition() == checkpoint.getPosition()) {
                return;
            }
            if (playerCheckpoint != null && checkpoint.getPosition() - playerCheckpoint.getPosition() > 1) {
                gamer.sendMessageLocale("PARKOUR_PREVIOUS_CHECKPOINT");
                return;
            }
            parkourWay.setCheckpoint(player, checkpoint);
            gamer.sendMessageLocale("PARKOUR_CHECKPOINT", checkpoint.getPosition(), parkourWay.getCheckpoints().size());
        }
    }

    @EventHandler(priority=EventPriority.LOWEST)
    private void onChangeSetting(GamerChangeSettingEvent event) {
        if (this.parkourManager.getPlayerParkour(event.getGamer().getPlayer()) != null && event.getSetting() == SettingsType.FLY && event.isEnable()) {
            event.setCancelled(true);
            event.getGamer().sendMessageLocale("PARKOUR_FLY_DISABLED");
        }
    }

    private String format(long ticks) {
        int millis = (int) (ticks % 1000 / 10);
        return (ticks / 1000L) + "." + (millis < 10 ? "0" + millis : Integer.valueOf(millis));
    }
}

