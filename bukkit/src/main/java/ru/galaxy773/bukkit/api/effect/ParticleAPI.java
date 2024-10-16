package ru.galaxy773.bukkit.api.effect;

import org.bukkit.Color;
import org.bukkit.FireworkEffect;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import java.util.Arrays;
import java.util.List;

public interface ParticleAPI {

    PlayerGlowing getOrCreateGlowing(Player owner, List<Player> players);
    PlayerGlowing getGlowing(Player owner);

    PlayerGlowing getByMember(Player member);

    void resetGlowing(Player player);

    List<PlayerGlowing> getGlowings();

    void shootRandomFirework(Player player);
    void shootRandomFirework(Location location);

    void launchInstantFirework(FireworkEffect fe, Player player);
    void launchInstantFirework(FireworkEffect fe, Location location);
    void launchInstantFirework(Location location, Color... colors);

    void sendEffect(ParticleEffect effect, Location center, float offsetX, float offsetY, float offsetZ, float speed, int amount, double range);
    void sendEffect(ParticleEffect effect, Location center, float speed, int amount);
    void sendEffect(ParticleEffect effect, Location center);

    void sendEffect(ParticleEffect effect, List<Player> players, Location center);
    void sendEffect(ParticleEffect effect, List<Player> players, Location center, float speed, int amount);

    void sendEffect(ParticleEffect effect, List<Player> players, Location center, float offsetX, float offsetY, float offsetZ, float speed, int amount);
    default void sendEffect(ParticleEffect effect, Location center, float offsetX, float offsetY, float offsetZ, float speed, int amount, Player... players) {
        sendEffect(effect, Arrays.asList(players), center, offsetX, offsetY, offsetZ, speed, amount);
    }

    void sendEffect(ParticleEffect effect, Location center, Vector direction, float speed, double range);
    void sendEffect(ParticleEffect effect, Material material, byte data, boolean block, Location center, Vector direction, float speed, double range);

    void sendEffect(ParticleEffect effect, List<Player> players, Location center, Vector direction, float speed);
    default void sendEffect(ParticleEffect effect, Location center, Vector direction, float speed, Player... players) {
        sendEffect(effect, Arrays.asList(players), center, direction, speed);
    }
    void sendEffect(ParticleEffect effect, List<Player> players, Material material, byte data, boolean block, Location center, Vector direction, float speed);
    default void sendEffect(ParticleEffect effect, Material material, byte data, boolean block, Location center, Vector direction, float speed, Player... players) {
        sendEffect(effect, Arrays.asList(players), material, data, block, center, direction, speed);
    }

    void sendEffect(ParticleEffect effect, Color color, Location center, double range);
    void sendEffect(ParticleEffect effect, int red, int green, int blue, Location center, double range);
    void sendEffect(ParticleEffect effect, int note, Location center, double range); //цветные ноты
    void sendEffect(ParticleEffect effect, Material material, byte data, boolean block, Location center, float offsetX, float offsetY, float offsetZ, float speed, int amount, double range);
    
    void sendEffect(ParticleEffect effect, List<Player> players, Color color, Location center);
    default void sendEffect(ParticleEffect effect, Color color, Location center, Player... players) {
        sendEffect(effect, Arrays.asList(players), color, center);
    }

    void sendEffect(ParticleEffect effect, List<Player> players, int red, int green, int blue, Location center);
    default void sendEffect(ParticleEffect effect, int red, int green, int blue, Location center, Player... players) {
        sendEffect(effect, Arrays.asList(players), red, green, blue, center);
    }

    void sendEffect(ParticleEffect effect, List<Player> players, int note, Location center);
    default void sendEffect(ParticleEffect effect, int note, Location center, Player... players) {
        sendEffect(effect, Arrays.asList(players), note, center);
    }

    void sendEffect(ParticleEffect effect, List<Player> players, Material material, byte data, boolean block, Location center, float offsetX, float offsetY, float offsetZ, float speed, int amount);
    default void sendEffect(ParticleEffect effect, Material material, byte data, boolean block, Location center, float offsetX, float offsetY, float offsetZ, float speed, int amount, Player... players) {
        sendEffect(effect, Arrays.asList(players), material, data, block, center, offsetX, offsetY, offsetZ, speed, amount);
    }

}
