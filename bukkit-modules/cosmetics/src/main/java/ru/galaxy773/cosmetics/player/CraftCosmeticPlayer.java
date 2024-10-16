package ru.galaxy773.cosmetics.player;

import com.google.common.collect.Multimap;
import org.bukkit.entity.Player;
import ru.galaxy773.bukkit.api.BukkitAPI;
import ru.galaxy773.bukkit.api.effect.ParticleEffect;
import ru.galaxy773.bukkit.api.entity.EntityAPI;
import ru.galaxy773.bukkit.api.gamer.BukkitGamer;
import ru.galaxy773.bukkit.api.gamer.GamerManager;
import ru.galaxy773.cosmetics.Cosmetics;
import ru.galaxy773.cosmetics.api.EffectType;
import ru.galaxy773.cosmetics.api.player.CosmeticPlayer;
import ru.galaxy773.cosmetics.sql.CosmeticLoader;

import java.util.Map;

public class CraftCosmeticPlayer implements CosmeticPlayer {

    private static final Cosmetics JAVA_PLUGIN = Cosmetics.getInstance();
    private static final EntityAPI ENTITY_API = BukkitAPI.getEntityAPI();

    private final Player player;
    private final BukkitGamer gamer;
    private final Multimap<EffectType, ParticleEffect> particles;
    private final Map<EffectType, ParticleEffect> selectedData;

    public CraftCosmeticPlayer(Player player, Multimap<EffectType, ParticleEffect> particles, Map<EffectType, ParticleEffect> selectedData) {
        this.player = player;
        this.gamer = GamerManager.getGamer((Player)player);
        this.particles = particles;
        this.selectedData = selectedData;
    }

    @Override
    public ParticleEffect getSelectedParticle(EffectType effectType) {
        return this.selectedData.get(effectType);
    }

    @Override
    public void setSelectedParticle(EffectType effectType, ParticleEffect particleEffect) {
        boolean insert = this.selectedData.get(effectType) == null;
        this.selectedData.put(effectType, particleEffect);
        CosmeticLoader.setSelectedEffect(this.gamer.getPlayerID(), effectType, particleEffect, insert);
    }

    @Override
    public void addParticle(EffectType effectType, ParticleEffect particleEffect) {
        this.particles.put(effectType, particleEffect);
        CosmeticLoader.addEffect(this.gamer.getPlayerID(), effectType, particleEffect);
    }

    @Override
    public Player getPlayer() {
        return this.player;
    }

    @Override
    public Multimap<EffectType, ParticleEffect> getParticles() {
        return this.particles;
    }
}

