package ru.galaxy773.achievements.listeners.categories;

import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import ru.galaxy773.achievements.Achievements;
import ru.galaxy773.achievements.api.AchievementType;
import ru.galaxy773.achievements.listeners.AchievementListener;
import ru.galaxy773.achievements.manager.AchievementManager;
import ru.galaxy773.achievements.player.AchievementPlayer;

public class SurvivalAchievementListener extends AchievementListener {

    public SurvivalAchievementListener(Achievements plugin, AchievementManager manager) {
        super(plugin, manager);
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onEntityDeath(EntityDeathEvent event) {
        LivingEntity entity = event.getEntity();
        if (entity.getType() != EntityType.ENDER_DRAGON)
            return;
        
        EntityDamageEvent entityDamageEvent = entity.getLastDamageCause();
        if (!(entityDamageEvent instanceof EntityDamageByEntityEvent))
            return;
        
        EntityDamageByEntityEvent damageEvent = (EntityDamageByEntityEvent) entityDamageEvent;
        Entity damagerEntity = damageEvent.getDamager();
        if (damagerEntity == null)
            return;
        
        Player damager = null;
        if (damagerEntity instanceof Player) {
            damager = (Player) damagerEntity;
        } else {
            if (damagerEntity instanceof Projectile) {
                Projectile projectile = (Projectile) damagerEntity;
                if (projectile.getShooter() != null && projectile.getShooter() instanceof Player)
                    damager = (Player) projectile.getShooter();
            }
        }

        if (damager != null) {
            AchievementPlayer player = this.manager.getFromCacheOrLoad(damager.getName());
            player.addProgress(AchievementType.DRAGON_SLAYER, 1);
        }
    }
}
