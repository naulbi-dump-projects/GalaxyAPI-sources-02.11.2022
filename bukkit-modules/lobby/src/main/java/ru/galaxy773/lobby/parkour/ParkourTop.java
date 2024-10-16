package ru.galaxy773.lobby.parkour;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import ru.galaxy773.bukkit.api.tops.hologram.HoloTop;
import ru.galaxy773.bukkit.api.tops.hologram.HoloTopData;
import ru.galaxy773.lobby.Lobby;
import ru.galaxy773.lobby.api.parkour.ParkourManager;
import ru.galaxy773.multiplatform.api.gamer.IBaseGamer;
import ru.galaxy773.multiplatform.api.locale.Lang;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ParkourTop {

    private final ParkourManager parkourManager;
    private final List<HoloTop> parkourTops = new ArrayList<>();

    public ParkourTop(Lobby javaPlugin, ParkourManager parkourManager) {
        this.parkourManager = parkourManager;
        this.parkourManager.getWays().forEach(parkourWay -> this.parkourTops.add(new ParkourHoloTop(javaPlugin, parkourWay.getId(), parkourWay.getTopLocation(), Lang.getList("PARKOUR_HOLO_TOP_HEADER"), 15)));
        Bukkit.getScheduler().runTaskTimerAsynchronously(javaPlugin, () -> this.parkourTops.forEach(HoloTop::update), 5L, 18000L);
    }

    private String format(long ticks) {
        int millis = (int) (ticks % 1000 / 10);
        return (ticks / 1000L) + "." + (millis < 10 ? "0" + millis : Integer.valueOf(millis));
    }

    private final class ParkourHoloTop extends HoloTop {

        private final int wayID;

        ParkourHoloTop(JavaPlugin javaPlugin, int wayID, Location location, List<String> topHeader, int updateMinutes) {
            super(javaPlugin, location, topHeader, updateMinutes);
            this.wayID = wayID;
        }

        public void updateData() {
            Map<IBaseGamer, Long> topData = ParkourLoader.getTop(this.wayID, 10);
            ArrayList<HoloTopData> holoTopData = new ArrayList<HoloTopData>();
            int index = 1;
            for (Map.Entry<IBaseGamer, Long> entry : topData.entrySet()) {
                holoTopData.add(new HoloTopData(entry.getKey().getDisplayName(), "\u00a7a" + ParkourTop.this.format(entry.getValue()) + " \u00a7f\u0441\u0435\u043a.", index));
                ++index;
            }
            this.topData = holoTopData;
        }
    }
}

