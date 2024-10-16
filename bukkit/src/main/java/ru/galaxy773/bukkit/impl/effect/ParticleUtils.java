package ru.galaxy773.bukkit.impl.effect;

import lombok.experimental.UtilityClass;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import ru.galaxy773.bukkit.api.effect.ParticleEffect;
import ru.galaxy773.bukkit.api.effect.ParticleProperty;
import ru.galaxy773.bukkit.impl.effect.data.*;

import java.util.List;

@UtilityClass
class ParticleUtils {
    Color getColour(final int c) {
        switch (c) {
            default: {
                return Color.AQUA;
            }
            case 2: {
                return Color.BLACK;
            }
            case 3: {
                return Color.BLUE;
            }
            case 4: {
                return Color.FUCHSIA;
            }
            case 5: {
                return Color.GRAY;
            }
            case 6: {
                return Color.GREEN;
            }
            case 7: {
                return Color.LIME;
            }
            case 8: {
                return Color.MAROON;
            }
            case 9: {
                return Color.NAVY;
            }
            case 10: {
                return Color.OLIVE;
            }
            case 11: {
                return Color.ORANGE;
            }
            case 12: {
                return Color.PURPLE;
            }
            case 13: {
                return Color.RED;
            }
            case 14: {
                return Color.SILVER;
            }
            case 15: {
                return Color.TEAL;
            }
            case 16: {
                return Color.WHITE;
            }
            case 17: {
                return Color.YELLOW;
            }
        }
    }

    boolean isWater(Location location) {
        Material material = location.getBlock().getType();
        return material == Material.WATER || material == Material.STATIONARY_WATER;
    }

    boolean isLongDistance(Location location, List<Player> players) {
        String world = location.getWorld().getName();
        for (Player player : players) {
            Location playerLocation = player.getLocation();
            if (!world.equals(playerLocation.getWorld().getName()) || playerLocation.distanceSquared(location) < 65536) {
                continue;
            }
            return true;
        }
        return false;
    }

    boolean isDataCorrect(ParticleEffect effect, CraftParticleData data) {
        return ((effect == ParticleEffect.BLOCK_CRACK
                || effect == ParticleEffect.BLOCK_DUST) && data instanceof CraftParticleBlockData)
                || (effect == ParticleEffect.ITEM_CRACK && data instanceof CraftParticleItemData);
    }

    boolean isColorCorrect(ParticleEffect effect, CraftParticleColor color) {
        return ((effect == ParticleEffect.SPELL_MOB
                || effect == ParticleEffect.SPELL_MOB_AMBIENT
                || effect == ParticleEffect.REDSTONE) && color instanceof CraftParticleOrdinaryColor)
                || (effect == ParticleEffect.NOTE && color instanceof CraftParticleNoteColor);
    }

    boolean hasProperty(ParticleEffect effect, ParticleProperty property) {
        return effect.getProperties().contains(property);
    }
}
