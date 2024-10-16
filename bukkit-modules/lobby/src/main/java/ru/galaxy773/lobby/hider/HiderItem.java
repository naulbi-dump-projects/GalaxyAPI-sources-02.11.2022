package ru.galaxy773.lobby.hider;

import lombok.experimental.UtilityClass;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import ru.galaxy773.bukkit.api.BukkitAPI;
import ru.galaxy773.bukkit.api.effect.ParticleAPI;
import ru.galaxy773.bukkit.api.effect.ParticleEffect;
import ru.galaxy773.bukkit.api.event.gamer.GamerChangeSettingEvent;
import ru.galaxy773.bukkit.api.gamer.BukkitGamer;
import ru.galaxy773.bukkit.api.gamer.GamerManager;
import ru.galaxy773.bukkit.api.usableitem.UsableAPI;
import ru.galaxy773.bukkit.api.utils.bukkit.BukkitUtil;
import ru.galaxy773.bukkit.api.utils.bukkit.HeadUtil;
import ru.galaxy773.bukkit.api.utils.bukkit.SoundUtil;
import ru.galaxy773.lobby.customitems.CustomItem;
import ru.galaxy773.multiplatform.api.gamer.constants.SettingsType;
import ru.galaxy773.multiplatform.api.locale.Lang;
import ru.galaxy773.multiplatform.api.sound.SoundType;
import ru.galaxy773.multiplatform.api.utils.cooldown.Cooldown;

import java.util.Random;

@UtilityClass
public final class HiderItem {

    private final ParticleAPI PARTICLE_API = BukkitAPI.getParticleAPI();
    private final UsableAPI USABLE_API = BukkitAPI.getUsableAPI();
    private final Random RANDOM = new Random();
    private final CustomItem enableItem = new CustomItem(HeadUtil.getHead("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvN2JkNDdkZDdjMzMzNmU3NWE2NjM5MWNkZjljOTM1ZmFlY2E4Y2UzOGFlMjJhMWIyNzg5NWUzMGI0NTI0NWE4In19fQ=="), "HIDER_ENABLE", (player, click, clickType) -> {
        if (Cooldown.hasCooldown(player.getName(), "HIDER")) {
            player.sendMessage(Lang.getMessage("SETTING_HIDER_COOLDOWN", Cooldown.getCooldownLeft(player.getName(), "HIDER")));
            SoundUtil.play(player, SoundType.NOTE_BASS);
            return;
        }
        SoundUtil.play(player, SoundType.NOTE_BELL);
        Cooldown.addCooldown(player.getName(), "HIDER", 200L);
        HiderItem.setSettings(player, false);
    });
    private final CustomItem disableItem = new CustomItem(HeadUtil.getHead("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZjEyZjc4N2M1NGRkODlkMTI2OThkZDE3YjU2NTEyOTRjZmI4MDE3ZDZhZDRkMjZlZTZhOTFjZjFkMGMxYzQifX19"), "HIDER_DISABLE", (player, click, clickType) -> {
        if (Cooldown.hasCooldown(player.getName(), "HIDER")) {
            player.sendMessage(Lang.getMessage("SETTING_HIDER_COOLDOWN", Cooldown.getCooldownLeft(player.getName(), "HIDER")));
            SoundUtil.play(player, SoundType.NOTE_BASS);
            return;
        }
        SoundUtil.play(player, SoundType.NOTE_BELL);
        Cooldown.addCooldown(player.getName(), "HIDER", 200L);
        HiderItem.setSettings(player, true);
    });

    public void giveToPlayer(Player player, boolean enable) {
        if (enable) {
            enableItem.givePlayer(player, 7);
        } else {
            disableItem.givePlayer(player, 7);
        }
    }

    public void setSettings(Player player, boolean result) {
        BukkitGamer gamer = GamerManager.getGamer(player);
        HiderItem.giveToPlayer(player, result);
        gamer.setSetting(SettingsType.HIDER, result, true);
        BukkitUtil.callEvent(new GamerChangeSettingEvent(gamer, SettingsType.HIDER, result));
        for (Player otherPlayer : Bukkit.getOnlinePlayers()) {
            if (otherPlayer == player) continue;
            HiderItem.effect(otherPlayer, player);
            if (result) {
                player.hidePlayer(otherPlayer);
                continue;
            }
            player.showPlayer(otherPlayer);
        }
    }

    private void effect(Player player, Player owner) {
        for (int i = 0; i < 9; ++i) {
            Location location = player.getLocation().clone().add(0.0, 1.0, 0.0).add(Math.random() * (double)(RANDOM.nextInt() % 2 == 0 ? 1 : -1), Math.random() * (double)(RANDOM.nextInt() % 2 == 0 ? 1 : -1), Math.random() * (double)(RANDOM.nextInt() % 2 == 0 ? 1 : -1));
            PARTICLE_API.sendEffect(ParticleEffect.SPELL, location, 0.1f, 0.1f, 0.1f, 0.1f, 2, owner);
        }
    }
}

