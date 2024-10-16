package ru.galaxy773.bukkit.impl.effect;

import org.bukkit.Color;
import org.bukkit.FireworkEffect;
import org.bukkit.Location;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Firework;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.meta.FireworkMeta;
import org.bukkit.metadata.FixedMetadataValue;
import ru.galaxy773.bukkit.api.BukkitPlugin;
import ru.galaxy773.bukkit.api.utils.listener.FastListener;
import ru.galaxy773.bukkit.nms.api.NmsAPI;
import ru.galaxy773.bukkit.nms.interfaces.NmsManager;

import java.util.Random;

public class CraftFireworkListener {

    private final BukkitPlugin javaPlugin = BukkitPlugin.getInstance();
    private final Random random = new Random();
    private final NmsManager nmsManager = NmsAPI.getManager();

    private final FireworkEffect.Type[] TypeList = new FireworkEffect.Type[] {
        FireworkEffect.Type.BALL, FireworkEffect.Type.BALL_LARGE,
                FireworkEffect.Type.BURST, FireworkEffect.Type.CREEPER,
                FireworkEffect.Type.STAR };

    public CraftFireworkListener() {
        FastListener.create().easyEvent(EntityDamageByEntityEvent.class, (event) -> {
            if (event.getDamager().getType() == EntityType.FIREWORK && event.getDamager().hasMetadata("BukkitAPI_FW")) {
                event.setCancelled(true);
            }
        }).register(BukkitPlugin.getInstance());
    }

    public void shootRandomFirework(Location location) {
        Firework fw = location.getWorld().spawn(location, Firework.class);
        FireworkMeta fm = fw.getFireworkMeta();
        FireworkEffect.Type type = TypeList[random.nextInt(5)];
        Color color1 = ParticleUtils.getColour(random.nextInt(16) + 1);
        Color color2 = ParticleUtils.getColour(random.nextInt(16) + 1);
        FireworkEffect effect = FireworkEffect.builder().flicker(random.nextBoolean())
                .withColor(color1)
                .withFade(color2)
                .with(type)
                .trail(random.nextBoolean()).build();
        fm.addEffect(effect);
        fm.setPower(random.nextInt(2) + 1);
        fw.setFireworkMeta(fm);
    }

    public void launchInstantFirework(FireworkEffect fe, Location location) {
        Firework firework = location.getWorld().spawn(location, Firework.class);
        FireworkMeta fm = firework.getFireworkMeta();
        fm.addEffect(fe);
        firework.setFireworkMeta(fm);

        firework.setMetadata("BukkitAPI_FW", new FixedMetadataValue(javaPlugin, firework.toString()));

        nmsManager.launchInstantFirework(firework);
    }
}
