package ru.galaxy773.bukkit.impl.effect.data;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.Material;

@SuppressWarnings("deprecation")
public abstract class CraftParticleData {
    @Getter
    @Setter
    private Material material;
    @Getter
    private final int[] packetData;

    public CraftParticleData(Material material, byte data) {
        this.material = material;
        this.packetData = new int[] { material.getId(), data };
    }
}
