package ru.galaxy773.lobby.top;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import ru.galaxy773.bukkit.api.BukkitAPI;
import ru.galaxy773.bukkit.api.gamer.GamerManager;
import ru.galaxy773.bukkit.api.hologram.Hologram;
import ru.galaxy773.bukkit.api.hologram.HologramAPI;
import ru.galaxy773.bukkit.api.tops.HologramAnimation;
import ru.galaxy773.bukkit.api.tops.armorstand.StandTopData;
import ru.galaxy773.bukkit.api.tops.armorstand.Top;
import ru.galaxy773.bukkit.api.tops.armorstand.TopManager;
import ru.galaxy773.bukkit.api.utils.bukkit.ItemBuilder;
import ru.galaxy773.lobby.Lobby;
import ru.galaxy773.lobby.config.TopConfig;
import ru.galaxy773.multiplatform.api.gamer.GamerAPI;
import ru.galaxy773.multiplatform.api.gamer.IBaseGamer;
import ru.galaxy773.multiplatform.api.locale.Lang;
import ru.galaxy773.multiplatform.api.utils.StringUtil;
import ru.galaxy773.multiplatform.api.utils.time.TimeUtil;

public class LobbyTop {

    private final HologramAPI hologramAPI = BukkitAPI.getHologramAPI();
    private final Lobby javaPlugin;
    private final TopManager topManager;

    public LobbyTop(Lobby javaPlugin, TopConfig topConfig) {
        this.javaPlugin = javaPlugin;
        this.topManager = new TopManager((JavaPlugin)javaPlugin);
        this.topManager.createTop(new Top(this.topManager, topConfig.getMiddleLocation().clone(), this.createMiddleHologram(topConfig.getMiddleLocation(), Lang.getList("HOLOGRAM_LEVEL_TOP_LINES"))), topConfig.getStandLocations());
        this.topManager.createTop(new Top(this.topManager, topConfig.getMiddleLocation().clone(), this.createMiddleHologram(topConfig.getMiddleLocation(), Lang.getList("HOLOGRAM_ONLINE_TOP_LINES"))), topConfig.getStandLocations());
        Bukkit.getScheduler().runTaskTimerAsynchronously((Plugin)this.javaPlugin, () -> {
            List<StandTopData> topData = new ArrayList<>();
            int position = 1;
            for (Map.Entry<IBaseGamer, Integer> entry : GamerAPI.getLevelTop(5).entrySet()) {
                topData.add(new TopData(this.topManager.getTop(0), entry.getKey(), "\u00a73" + ((IBaseGamer)entry.getKey()).getLevel() + " \u00a7f\u0443\u0440. " + StringUtil.onPercentBar((double)((IBaseGamer)entry.getKey()).getExp(), (double)((IBaseGamer)entry.getKey()).getTotalExpNextLevel()) + " \u00a76" + StringUtil.onPercent(((IBaseGamer)entry.getKey()).getExp(), ((IBaseGamer)entry.getKey()).getTotalExpNextLevel()) + "%", position));
                ++position;
            }
            position = 1;
            for (Map.Entry<IBaseGamer, Integer> entry : GamerAPI.getOnlineTop(5).entrySet()) {
                topData.add(new TopData(this.topManager.getTop(1), entry.getKey(), "\u00a7a" + TimeUtil.getTimeLeft(entry.getValue(), TimeUtil.TimeLeftFormat.MINUTES), position));
                ++position;
            }
            this.topManager.updateStandData(topData);
        }, 5L, 18000L);
    }

    private Hologram createMiddleHologram(Location location, List<String> lines) {
        Hologram middleHologram = this.hologramAPI.createHologram((JavaPlugin)this.javaPlugin, location.clone());
        lines.forEach(line -> {
            if (line.contains("{time}")) {
                middleHologram.addAnimationLine(20L, new HologramAnimation(15));
            } else if (line.contains("{item:")) {
                middleHologram.addDropLine(false, ItemBuilder.builder(line.split("\\{item:")[1].split("}")[0]).build());
            } else {
                middleHologram.addTextLine(line);
            }
        });
        return middleHologram;
    }
}

