package ru.galaxy773.lobby.presents;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.plugin.Plugin;
import ru.galaxy773.bukkit.api.BukkitAPI;
import ru.galaxy773.bukkit.api.effect.ParticleAPI;
import ru.galaxy773.bukkit.api.effect.ParticleEffect;
import ru.galaxy773.bukkit.api.event.gamer.async.AsyncGamerJoinEvent;
import ru.galaxy773.bukkit.api.gamer.BukkitGamer;
import ru.galaxy773.bukkit.api.gamer.GamerManager;
import ru.galaxy773.bukkit.api.utils.listener.GListener;
import ru.galaxy773.lobby.Lobby;
import ru.galaxy773.lobby.api.presents.PresentsManager;
import ru.galaxy773.lobby.config.PresentsConfig;
import ru.galaxy773.multiplatform.api.locale.Lang;
import ru.galaxy773.multiplatform.api.sound.SoundType;

public class PresentsListener extends GListener<Lobby> {

    private final ParticleAPI particleAPI = BukkitAPI.getParticleAPI();
    private final PresentsManager presentsManager;
    private final PresentsConfig presentsConfig;

    public PresentsListener(Lobby plugin, PresentsManager presentsManager, PresentsConfig presentsConfig) {
        super(plugin);
        this.presentsManager = presentsManager;
        this.presentsConfig = presentsConfig;
    }

    @EventHandler(ignoreCancelled=true, priority=EventPriority.HIGHEST)
    public void onInteract(PlayerInteractEvent e) {
        if (!e.hasBlock() || e.getClickedBlock().getType().name().equals("SKULL") || e.getClickedBlock().getType().name().equals("LEGACY_SKULL") || e.getHand() != EquipmentSlot.HAND) {
            return;
        }
        Player player = e.getPlayer();
        Location location = e.getClickedBlock().getLocation();
        if (!this.presentsManager.locationExists(e.getClickedBlock())) {
            return;
        }
        BukkitGamer gamer = GamerManager.getGamer(player);
        e.setCancelled(true);
        if (this.presentsManager.getFindedCount(player) >= this.presentsManager.getMaxCount()) {
            gamer.sendMessageLocale("PRESENTS_IS_COMPLETED");
            return;
        }
        if (!this.presentsManager.locationFind(player, location)) {
            gamer.sendMessageLocale("PRESENTS_IS_FINDED");
            return;
        }
        gamer.sendMessageLocale("PRESENTS_FIND", this.presentsManager.getFindedCount(player), this.presentsManager.getMaxCount());
        this.particleAPI.sendEffect(ParticleEffect.HEART, location.clone().add(0.5, 0.5, 0.5));
        gamer.playSound(SoundType.LEVEL_UP);
        gamer.sendActionBar(Lang.getMessage("PRESENTS_FIND_ACTION_BAR", this.presentsConfig.getRewardGold(), this.presentsConfig.getRewardExp()));
        if (this.presentsManager.getFindedCount(player) == this.presentsManager.getMaxCount()) {
            gamer.sendTitle(Lang.getMessage("PRESENTS_COMPLETE_TITLE"), Lang.getMessage("PRESENTS_COMPLETE_SUBTITLE"), 20, 40, 20);
            gamer.sendActionBar(Lang.getMessage("PRESENTS_FIND_ACTION_BAR", this.presentsConfig.getRewardGold() * 15));
            gamer.setGold(gamer.getGold() + this.presentsConfig.getRewardGold() * 15, true);
            gamer.addExp(this.presentsConfig.getRewardExp() * 15);
        }
        gamer.setGold(gamer.getGold() + this.presentsConfig.getRewardGold(), true);
        gamer.addExp(this.presentsConfig.getRewardExp());
    }

    @EventHandler(priority=EventPriority.HIGHEST)
    private void onAsyncJoin(AsyncGamerJoinEvent event) {
        this.presentsManager.loadPlayer(event.getGamer().getPlayer());
    }

    @EventHandler
    private void onQuit(PlayerQuitEvent event) {
        this.presentsManager.unloadPlayer(event.getPlayer());
    }
}

