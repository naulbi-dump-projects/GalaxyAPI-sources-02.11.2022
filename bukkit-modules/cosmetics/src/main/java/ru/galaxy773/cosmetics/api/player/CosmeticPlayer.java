package ru.galaxy773.cosmetics.api.player;

import com.google.common.collect.Multimap;
import org.bukkit.entity.Player;
import ru.galaxy773.bukkit.api.effect.ParticleEffect;
import ru.galaxy773.cosmetics.api.EffectType;

public interface CosmeticPlayer {

    Player getPlayer();

    Multimap<EffectType, ParticleEffect> getParticles();

    ParticleEffect getSelectedParticle(EffectType var1);

    void setSelectedParticle(EffectType var1, ParticleEffect var2);

    void addParticle(EffectType var1, ParticleEffect var2);
}

