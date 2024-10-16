package ru.galaxy773.lobby.parkour;

import org.bukkit.Bukkit;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import ru.galaxy773.bukkit.api.gamer.BukkitGamer;
import ru.galaxy773.bukkit.api.gamer.GamerManager;
import ru.galaxy773.lobby.Lobby;
import ru.galaxy773.lobby.api.parkour.ParkourManager;
import ru.galaxy773.multiplatform.api.locale.Lang;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class CraftParkourManager
implements ParkourManager {
    private final Map<Block, ParkourWay> ways = new HashMap<>();
    private final Map<String, Map<Integer, Long>> cachedData = new ConcurrentHashMap<>();

    public CraftParkourManager(Lobby javaPlugin) {
        Bukkit.getScheduler().runTaskTimerAsynchronously(javaPlugin, () -> Bukkit.getOnlinePlayers().forEach(player -> {
            ParkourWay parkourWay = this.getPlayerParkour(player);
            if (parkourWay != null) {
                BukkitGamer gamer = GamerManager.getGamer(player);
                gamer.sendActionBar(Lang.getMessage("PARKOUR_ACTION_BAR", this.format(this.getPlayerBestTime(player, parkourWay)), this.format(parkourWay.getTime(player))));
            }
        }), 0L, 20L);
    }

    @Override
    public void addParkourWay(ParkourWay parkourWay) {
        this.ways.put(parkourWay.getStartLocation().getBlock(), parkourWay);
    }

    @Override
    public void removeParkourWay(ParkourWay parkourWay) {
        this.removeParkourWay(parkourWay.getStartLocation().getBlock());
    }

    @Override
    public void removeParkourWay(Block block) {
        this.ways.remove(block);
    }

    @Override
    public ParkourWay getParkourWay(Block block) {
        return this.ways.get(block);
    }

    @Override
    public ParkourWay getPlayerParkour(Player player) {
        return this.ways.values().stream().filter(parkourWay -> parkourWay.isInWay(player)).findFirst().orElse(null);
    }

    @Override
    public void loadPlayer(Player player) {
        BukkitGamer gamer = GamerManager.getGamer(player);
        this.cachedData.put(player.getName(), ParkourLoader.getData(gamer.getPlayerID()));
    }

    @Override
    public void unloadPlayer(Player player) {
        this.cachedData.remove(player.getName());
        ParkourWay playerParkour = this.getPlayerParkour(player);
        if (playerParkour != null) {
            playerParkour.removePlayer(player);
        }
    }

    @Override
    public long getPlayerBestTime(Player player, ParkourWay parkourWay) {
        return this.cachedData.get(player.getName()).getOrDefault(parkourWay.getId(), -1L);
    }

    @Override
    public Collection<ParkourWay> getWays() {
        return this.ways.values();
    }

    @Override
    public void setPlayerBestTime(Player player, ParkourWay parkourWay, long time) {
        this.cachedData.get(player.getName()).put(parkourWay.getId(), time);
        BukkitGamer gamer = GamerManager.getGamer(player);
        ParkourLoader.setData(gamer.getPlayerID(), parkourWay.getId(), time);
    }

    private String format(long ticks) {
        if (ticks == -1L) {
            return "\u00a7c\u043d\u0435 \u0443\u0441\u0442\u0430\u043d\u043e\u0432\u043b\u0435\u043d\u043e";
        }
        int minutes = (int) (ticks / 1000 / 60);
        int seconds = (int) ((ticks / 1000L) % 60);
        return (minutes < 10 ? "0" + minutes : Integer.valueOf(minutes)) + ":" + (seconds < 10 ? "0" + seconds : Integer.valueOf(seconds));
    }
}

