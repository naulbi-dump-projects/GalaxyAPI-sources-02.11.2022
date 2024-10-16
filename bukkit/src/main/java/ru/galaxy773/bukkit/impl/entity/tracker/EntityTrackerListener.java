package ru.galaxy773.bukkit.impl.entity.tracker;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerChangedWorldEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import ru.galaxy773.bukkit.api.BukkitPlugin;
import ru.galaxy773.bukkit.api.event.player.AsyncChunkSendEvent;
import ru.galaxy773.bukkit.api.utils.bukkit.BukkitUtil;
import ru.galaxy773.bukkit.api.utils.bukkit.LocationUtil;
import ru.galaxy773.bukkit.api.utils.listener.GListener;

import java.util.Set;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class EntityTrackerListener extends GListener<BukkitPlugin> {

    private final TrackerManager trackerManager;

    public EntityTrackerListener(TrackerManager trackerManager) {
        super(BukkitPlugin.getInstance());
        this.trackerManager = trackerManager;

        Executors.newSingleThreadScheduledExecutor().scheduleAtFixedRate(() -> {
            for (Player player : Bukkit.getOnlinePlayers()) {
                String name = player.getName();
                if (!player.isOnline())
                    continue;

                for (TrackerEntity trackerEntity : trackerManager.getTrackerEntities()) {
                    if (!trackerEntity.isHeadLook())
                        continue;

                    Location location = trackerEntity.getLocation();
                    double distance = LocationUtil.distance(location, player.getLocation());
                    if (location.getWorld() == player.getWorld() && trackerEntity.canSee(player)) {
                        Set<String> names = trackerEntity.getHeadPlayers();
                        if (distance < 5 && distance != -1) {
                            names.add(name);
                            location = LocationUtil.faceEntity(trackerEntity.getLocation(), player).clone();
                            trackerEntity.sendHeadRotation(player, location.getYaw(), location.getPitch());
                        } else if (names.contains(name)) {
                            names.remove(name);
                            trackerEntity.sendHeadRotation(player, location.getYaw(), location.getPitch());
                        }

                    }
                }
            }
        }, 5, 50, TimeUnit.MILLISECONDS);
    }

    @EventHandler
    public void onJoin(AsyncChunkSendEvent e) {
        Player player = e.getPlayer();
        if (player == null)
            return;

        String worldName = e.getWorldName();
        int x = e.getX();
        int z = e.getZ();

        for (TrackerEntity trackerEntity : trackerManager.getTrackerEntities()) {
            if (!trackerEntity.getLocation().getWorld().getName().equalsIgnoreCase(worldName))
                continue;

            int trackedEntityX = trackerEntity.getLocation().getBlockX() >> 4;
            int trackedEntityZ = trackerEntity.getLocation().getBlockZ() >> 4;
            if (trackedEntityX == x && trackedEntityZ == z && trackerEntity.canSee(player)) {
                trackerEntity.destroy(player);
                BukkitUtil.runTaskLaterAsync(10L, () -> trackerEntity.spawn(player));
            }
        }
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent e) {
        Player player = e.getPlayer();
        if (player == null) {
            return;
        }

        for (TrackerEntity trackerEntity : trackerManager.getTrackerEntities()) {
            trackerEntity.removeTo(player);
            if (trackerEntity.getOwner() != null && player.getName().equals(trackerEntity.getOwner().getName()))
                trackerEntity.remove();
        }
    }

    @EventHandler
    public void onChangeWorld(PlayerChangedWorldEvent e) {
        Player player = e.getPlayer();

        BukkitUtil.runTaskAsync(() -> {
            for (TrackerEntity trackerEntity : trackerManager.getTrackerEntities()) {
                if (trackerEntity.getLocation().getWorld() == e.getFrom() && trackerEntity.canSee(player)) {
                    trackerEntity.destroy(player);
                }
            }
        });
    }
}
