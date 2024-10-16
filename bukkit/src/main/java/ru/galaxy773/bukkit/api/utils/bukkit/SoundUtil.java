package ru.galaxy773.bukkit.api.utils.bukkit;

import lombok.experimental.UtilityClass;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import ru.galaxy773.multiplatform.api.sound.SoundType;

@UtilityClass
public class SoundUtil {

    public void play(Player player, SoundType soundType) {
        play(player, Sound.valueOf(soundType.getSoundName()));
    }

    public void play(Location location, SoundType soundType) {
        play(location, Sound.valueOf(soundType.getSoundName()));
    }

    public void broadcast(SoundType soundType) {
        broadcast(Sound.valueOf(soundType.getSoundName()));
    }

    public void playInRadius(Location location, SoundType soundType, double radius) {
        playInRadius(location, Sound.valueOf(soundType.getSoundName()), radius);
    }

    public void play(Player player, Sound sound) {
        player.playSound(player.getLocation(), sound, 1f, 1f);
    }

    public void play(Location location, Sound sound) {
        location.getWorld().playSound(location, sound, 1f, 1f);
    }

    public void broadcast(Sound sound) {
        Bukkit.getOnlinePlayers().forEach(player -> play(player, sound));
    }

    public void playInRadius(Location location, Sound sound, double radius) {
        location.getWorld().getPlayers().stream()
                .filter(player -> LocationUtil.distance(player.getLocation(), location) <= radius)
                .forEach(player -> play(player, sound));
    }
}
