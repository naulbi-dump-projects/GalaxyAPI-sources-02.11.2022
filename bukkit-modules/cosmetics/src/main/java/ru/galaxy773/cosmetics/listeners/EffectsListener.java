package ru.galaxy773.cosmetics.listeners;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import net.minecraft.server.v1_12_R1.EntityPlayer;
import org.bukkit.craftbukkit.v1_12_R1.entity.CraftPlayer;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import ru.galaxy773.bukkit.api.BukkitAPI;
import ru.galaxy773.bukkit.api.effect.ParticleAPI;
import ru.galaxy773.bukkit.api.effect.ParticleEffect;
import ru.galaxy773.bukkit.api.event.player.PlayerKillEvent;
import ru.galaxy773.bukkit.api.utils.listener.GListener;
import ru.galaxy773.cosmetics.Cosmetics;
import ru.galaxy773.cosmetics.api.CosmeticsAPI;
import ru.galaxy773.cosmetics.api.EffectType;
import ru.galaxy773.cosmetics.api.manager.CosmeticManager;
import ru.galaxy773.cosmetics.api.player.CosmeticPlayer;

public class EffectsListener extends GListener<Cosmetics> {

    private final ParticleAPI particleAPI = BukkitAPI.getParticleAPI();
    private final CosmeticManager cosmeticManager = CosmeticsAPI.getCosmeticManager();
    private final Map<Arrow, ParticleEffect> arrows = new ConcurrentHashMap<>();

    public EffectsListener(Cosmetics javaPlugin) {
        super(javaPlugin);
        new BukkitRunnable(){

            public void run() {
                for (Map.Entry<Arrow, ParticleEffect> entry : EffectsListener.this.arrows.entrySet()) {
                    Arrow arrow = entry.getKey();
                    if (arrow == null || arrow.isDead()) {
                        EffectsListener.this.arrows.remove(arrow);
                        continue;
                    }
                    EffectsListener.this.particleAPI.sendEffect(
                            entry.getValue(),
                            arrow.getLocation(),
                            0.0f,
                            0.0f,
                            0.0f,
                            0.1f,
                            5,
                            20.0);
                }
            }
        }.runTaskTimerAsynchronously(javaPlugin, 5L, 1L);
    }

    @EventHandler
    public void onKill(PlayerKillEvent e) {
        CosmeticPlayer cosmeticPlayer = this.cosmeticManager.getCosmeticPlayer(e.getPlayer());
        if (cosmeticPlayer == null) {
            return;
        }
        ParticleEffect particleEffect = cosmeticPlayer.getSelectedParticle(EffectType.KILLS);
        if (particleEffect == null) {
            return;
        }
        this.particleAPI.sendEffect(particleEffect, e.getPlayer().getLocation().clone().add(0.0, 1.0, 0.0), 0.25f, 0.25f, 0.25f, 0.1f, 15, 12.0);
    }

    @EventHandler(priority=EventPriority.HIGHEST, ignoreCancelled=true)
    public void onEntityDamage(EntityDamageByEntityEvent e) {
        if (!(e.getEntity() instanceof Player)) {
            return;
        }
        if (!(e.getDamager() instanceof Player)) {
            return;
        }
        Player player = (Player)e.getDamager();
        CosmeticPlayer cosmeticPlayer = this.cosmeticManager.getCosmeticPlayer(player);
        if (cosmeticPlayer == null) {
            return;
        }
        ParticleEffect particleEffect = cosmeticPlayer.getSelectedParticle(EffectType.CRITS);
        if (particleEffect == null) {
            return;
        }
        if (player.isOnGround() || player.isInsideVehicle() || player.isSprinting() || player.getFallDistance() <= 0.0f || player.hasPotionEffect(PotionEffectType.BLINDNESS) || player.hasMetadata("swing-time") && ((MetadataValue)player.getMetadata("swing-time").get(0)).asFloat() <= 0.9f) {
            return;
        }
        EntityPlayer entityPlayer = ((CraftPlayer)player).getHandle();
        if (entityPlayer.m_() || entityPlayer.isInWater()) {
            return;
        }
        this.particleAPI.sendEffect(particleEffect, e.getEntity().getLocation().clone().add(0.0, 1.0, 0.0), 0.25f, 0.25f, 0.25f, 0.1f, 15, 12.0);
    }

    @EventHandler(priority=EventPriority.HIGHEST, ignoreCancelled=true)
    public void onArrowShoot(EntityShootBowEvent e) {
        if (!(e.getEntity() instanceof Player)) {
            return;
        }
        if (!(e.getProjectile() instanceof Arrow)) {
            return;
        }
        CosmeticPlayer cosmeticPlayer = this.cosmeticManager.getCosmeticPlayer((Player)e.getEntity());
        if (cosmeticPlayer == null) {
            return;
        }
        ParticleEffect particleEffect = cosmeticPlayer.getSelectedParticle(EffectType.ARROWS);
        if (particleEffect == null) {
            return;
        }
        this.arrows.put((Arrow)e.getProjectile(), particleEffect);
    }

    @EventHandler(priority=EventPriority.HIGHEST, ignoreCancelled=true)
    public void onProjectileHit(ProjectileHitEvent e) {
        if (!(e.getEntity() instanceof Arrow)) {
            return;
        }
        this.arrows.remove(((Arrow)e.getEntity()));
    }
}

