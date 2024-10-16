package ru.galaxy773.bukkit.api.utils.bukkit;

import lombok.experimental.UtilityClass;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import ru.galaxy773.bukkit.api.BukkitAPI;
import ru.galaxy773.bukkit.api.BukkitPlugin;
import ru.galaxy773.bukkit.api.gamer.BukkitGamer;
import ru.galaxy773.bukkit.api.gamer.GamerManager;
import ru.galaxy773.bukkit.api.worldborder.BorderAPI;
import ru.galaxy773.bukkit.nms.api.NmsAPI;
import ru.galaxy773.bukkit.nms.interfaces.NmsManager;
import ru.galaxy773.multiplatform.api.game.GameState;
import ru.galaxy773.multiplatform.api.gamer.GamerAPI;
import ru.galaxy773.multiplatform.api.locale.Lang;
import ru.galaxy773.multiplatform.api.sound.SoundType;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@UtilityClass
public class PlayerUtil {

    private final NmsManager NMS_MANAGER = NmsAPI.getManager();
    private final BorderAPI BORDER_API = BukkitAPI.getBorderAPI();

    public Player getDamager(Entity entity) {
        if (entity instanceof Player) {
            return (Player) entity;
        }
        Entity damager = null;
        if (entity instanceof Projectile) {
            Projectile projectile = (Projectile) entity;
            damager = (projectile.getShooter() instanceof Entity) ? (Entity) projectile.getShooter() : null;
        }
        if (damager == null) {
            return null;
        }
        if (damager instanceof Player) {
            return (Player) damager;
        }
        return null;
    }

    public static Collection<Player> getAlivePlayers() {
        Set<Player> players = new HashSet<>();
        for (String playerName : GamerAPI.getGamers().keySet()) {
            Player player = Bukkit.getPlayerExact(playerName);
            if (isAlive(player))
                players.add(player);

        }
        return players;
    }

    public static Collection<Player> getSpectators() {
        Set<Player> players = new HashSet<>();
        for (String playerName : GamerAPI.getGamers().keySet()) {
            Player player = Bukkit.getPlayerExact(playerName);
            if (player == null)
                continue;
            if (isSpectator(player))
                players.add(player);

        }
        return players;
    }

    public static Collection<Player> getGhosts() {
        Set<Player> players = new HashSet<>();
        for (String playerName : GamerAPI.getGamers().keySet()) {
            Player player = Bukkit.getPlayerExact(playerName);
            if (player == null)
                continue;
            if (isGhost(player))
                players.add(player);

        }
        return players;
    }

    public Collection<Player> getNearbyPlayers(Player player, int radius) {
        return player.getNearbyEntities(radius, radius, radius).stream()
                .filter(entity -> entity instanceof Player)
                .map(entity -> (Player) entity)
                .collect(Collectors.toCollection(HashSet::new));
    }

    public Collection<Player> getNearbyPlayers(Location location, int radius){
        return Bukkit.getOnlinePlayers().stream()
                .filter(player -> LocationUtil.distance(player.getLocation(), location) <= radius
                        && LocationUtil.distance(player.getLocation(), location) != -1)
                .collect(Collectors.toCollection(HashSet::new));
    }

    public static boolean isAlive(Player player) {
        if (player != null && player.isOnline()) {
            BukkitGamer gamer = GamerManager.getGamer(player);
            if (gamer != null){
              //  GameModeType gameModeType = gamer.getGameMode();
              //  return gameModeType != GameModeType.SPECTATOR;
            }
        }
        return false;
    }

    public static boolean isGhost(Player player) {
        if (player != null && player.isOnline()) {
            BukkitGamer gamer = GamerManager.getGamer(player);
            if (gamer != null) {
              //  GameModeType gameModeType = gamer.getGameMode();
               // return gameModeType == GameModeType.GHOST;
            }
        }
        return false;
    }

    public static boolean isSpectator(Player player) {
        if (player != null && player.isOnline()) {
            BukkitGamer gamer = GamerManager.getGamer(player);
            if (gamer != null) {
               // GameModeType gameModeType = gamer.getGameMode();
               // return gameModeType == GameModeType.SPECTATOR;
            }
        }
        return false;
    }

    public boolean havePotionEffectType(Player player, PotionEffectType potionEffectType) {
        return player.getActivePotionEffects().stream()
                .anyMatch(potionEffect -> potionEffect.getType() == potionEffectType);
    }

    public int getPotionEffectLevel(Player player, PotionEffectType potionEffectType) {
        PotionEffect effect = player.getActivePotionEffects().stream()
                .filter(potionEffect -> potionEffect.getType() == potionEffectType)
                .findFirst()
                .orElse(null);
        return effect != null ? effect.getAmplifier() : -1;
    }

    public void removePotionEffect(Player player, PotionEffectType potionEffectType) {
        player.removePotionEffect(potionEffectType);
    }

    public void addPotionEffect(Player player, PotionEffectType potionEffectType, int level){
        if (player == null) {
            return;
        }

        if (havePotionEffectType(player, potionEffectType))
            removePotionEffect(player, potionEffectType);

        player.addPotionEffect(new PotionEffect(potionEffectType, Integer.MAX_VALUE, level));
    }

    public void addPotionEffect(Player player, PotionEffectType potionEffectType, int level, int seconds){
        if (havePotionEffectType(player, potionEffectType))
            removePotionEffect(player, potionEffectType);

        player.addPotionEffect(new PotionEffect(potionEffectType, seconds*20, level));
    }

    public void reset(Player player) {
        if (player == null || !player.isOnline())
            return;

        try {
            player.getActivePotionEffects().forEach(potionEffect ->
                    player.removePotionEffect(potionEffect.getType()));

            player.setHealth(player.getMaxHealth());
            player.setFoodLevel(20);
            player.setExp(0.0f);
            player.setLevel(0);
            player.setFireTicks(0);

            player.closeInventory();
            player.getInventory().clear();
            player.getInventory().setArmorContents(null);
        } catch (Exception ignored) {}

        BukkitUtil.runTask(()-> {
            if (!player.isOnline())
                return;
            NMS_MANAGER.disableFire(player);
            NMS_MANAGER.removeArrowFromPlayer(player);
        });
    }

    public void setRespawn(Player player, int respawnTime, Runnable runnable) {
        BukkitGamer gamer = GamerManager.getGamer(player);
        if (gamer == null)
            return;
        
        reset(player);

        player.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, Integer.MAX_VALUE, 0));

        Bukkit.getOnlinePlayers().forEach(p -> p.hidePlayer(player));

        new BukkitRunnable(){

            String title = Lang.getMessage("DEATH_MSG_TITLE");
            String subTitle = Lang.getMessage("RESPAWN_SUBTITLE", String.valueOf(respawnTime));

            int time = respawnTime * 20;
            @Override
            public void run() {
                if (!player.isOnline()){
                    cancel();
                    return;
                }
                if (GameState.END == GameState.getCurrent()){
                    Bukkit.getOnlinePlayers().forEach(p -> p.showPlayer(player));
                    cancel();
                    return;
                }
                BORDER_API.sendRedScreen(player);
                player.setAllowFlight(true);
                player.setFlying(true);
               // player.teleport(GameSettings.getSpectatorLocation());
                if (time == 0){
                    Bukkit.getOnlinePlayers().forEach(p -> p.showPlayer(player));

                    player.removePotionEffect(PotionEffectType.INVISIBILITY);
                    title = Lang.getMessage("RESPAWN_TITLE");
                    gamer.sendTitle(title, " ", 10, 40, 10);

                    BukkitUtil.runTask(runnable);
                    BukkitUtil.runTask(() -> NMS_MANAGER.disableFire(player));

                    player.setAllowFlight(false);
                    player.setFlying(false);
                    player.setFallDistance(0);
                    cancel();
                    return;
                }
                if (time % 20 == 0) {
                    subTitle = Lang.getMessage("RESPAWN_SUBTITLE", String.valueOf(time / 20));
                    gamer.sendTitle(title, subTitle, 10, 40, 10);
                }
                time--;
            }
        }.runTaskTimer(BukkitPlugin.getInstance(), 5L, 1);

    }
    
    public void death(Player death) {
        Player killer = death.getKiller();
        reset(death);
        if (killer == null)
            return;

        BukkitGamer gamer = GamerManager.getGamer(killer);
        gamer.playSound(SoundType.POSITIVE);
    }
}
