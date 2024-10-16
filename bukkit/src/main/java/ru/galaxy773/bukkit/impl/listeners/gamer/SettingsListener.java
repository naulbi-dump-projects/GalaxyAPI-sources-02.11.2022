package ru.galaxy773.bukkit.impl.listeners.gamer;

import org.bukkit.Effect;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import ru.galaxy773.bukkit.api.BukkitPlugin;
import ru.galaxy773.bukkit.api.gamer.BukkitGamer;
import ru.galaxy773.bukkit.api.gamer.GamerManager;
import ru.galaxy773.bukkit.api.utils.bukkit.PlayerUtil;
import ru.galaxy773.bukkit.api.utils.listener.GListener;
import ru.galaxy773.multiplatform.api.gamer.constants.SettingsType;

public class SettingsListener extends GListener<BukkitPlugin> {

    public SettingsListener(BukkitPlugin javaPlugin) {
        super(javaPlugin);
    }

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onChat(AsyncPlayerChatEvent e) {
        BukkitGamer gamer = GamerManager.getGamer(e.getPlayer());
        if (!gamer.getSetting(SettingsType.CHAT)) {
            gamer.sendMessageLocale("SETTING_CHAT_IS_DISABLED");
            e.setCancelled(true);
            return;
        }
        e.getRecipients().removeIf(player -> !GamerManager.getGamer(player).getSetting(SettingsType.CHAT));
    }

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onEntityDamageByEntity(EntityDamageByEntityEvent e) {
        if (e.getEntity().getType() != EntityType.PLAYER)
            return;

        Player damager = PlayerUtil.getDamager(e.getEntity());
        if (damager == null)
            return;

        BukkitGamer gamer = GamerManager.getGamer(damager);
        if (gamer.getSetting(SettingsType.BLOOD))
            e.getEntity().getWorld().playEffect(
                    e.getEntity().getLocation().clone().add(-0.5, 1, -0.5),
                    Effect.STEP_SOUND, 50);
    }
}
