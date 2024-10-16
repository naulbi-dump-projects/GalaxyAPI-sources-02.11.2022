package ru.galaxy773.lobby.config;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.bukkit.Location;
import org.bukkit.configuration.ConfigurationSection;
import ru.galaxy773.bukkit.api.BukkitAPI;
import ru.galaxy773.bukkit.api.hologram.Hologram;
import ru.galaxy773.bukkit.api.hologram.HologramAPI;
import ru.galaxy773.bukkit.api.utils.bukkit.ItemBuilder;
import ru.galaxy773.bukkit.api.utils.bukkit.LocationUtil;
import ru.galaxy773.lobby.api.parkour.ParkourManager;
import ru.galaxy773.lobby.parkour.*;
import ru.galaxy773.multiplatform.api.locale.Lang;

public class ParkourConfig extends LobbyConfig {

    private final HologramAPI hologramAPI = BukkitAPI.getHologramAPI();
    private ParkourManager parkourManager;

    public ParkourConfig() {
        super("parkour.yml");
    }

    @Override
    public void load() {
        this.parkourManager = new CraftParkourManager(JAVA_PLUGIN);
        ConfigurationSection section = this.config.getConfigurationSection("ways");
        section.getValues(false).keySet().forEach(idLine -> {
            if (!StringUtils.isNumeric(idLine)) {
                throw new IllegalArgumentException("Way ID not numeric");
            }
            int id = Integer.parseInt(idLine);
            ArrayList<ParkourCheckpoint> checkpoints = new ArrayList<ParkourCheckpoint>();
            int index = 1;
            for (String checkpoint : section.getStringList(idLine + ".checkpoints")) {
                checkpoints.add(new ParkourCheckpoint(LocationUtil.stringToLocation(checkpoint, false), index));
                ++index;
            }
            this.parkourManager.addParkourWay(new ParkourWay(id, LocationUtil.stringToLocation(section.getString(idLine + ".start_location"), (boolean)true), LocationUtil.stringToLocation(section.getString(idLine + ".end_location"), (boolean)true), LocationUtil.stringToLocation(section.getString(idLine + ".top_location"), (boolean)false), checkpoints, section.getInt(idLine + ".gold_reward"), section.getInt(idLine + ".exp_reward")));
        });
    }

    @Override
    public void init() {
        this.parkourManager.getWays().forEach(parkourWay -> {
            this.createHologram(parkourWay.getStartLocation().clone().add(0.5, 1.0, 0.5), Lang.getList("PARKOUR_START_HOLOGRAM_LINES", parkourWay.getGoldReward(), parkourWay.getExpReward()));
            this.createHologram(parkourWay.getEndLocation().clone().add(0.5, 1.0, 0.5), Lang.getList("PARKOUR_END_HOLOGRAM_LINES"));
            parkourWay.getCheckpoints().forEach(parkourCheckpoint -> this.createHologram(parkourCheckpoint.getLocation().clone().add(0.5, 1.0, 0.5), Lang.getList("PARKOUR_CHECKPOINT_HOLOGRAM_LINES", parkourCheckpoint.getPosition())));
        });
        new ParkourTop(JAVA_PLUGIN, this.parkourManager);
        new ParkourListener(JAVA_PLUGIN, this.parkourManager);
    }

    private void createHologram(Location location, List<String> lines) {
        Hologram hologram = this.hologramAPI.createHologram(JAVA_PLUGIN, location);
        lines.forEach(line -> {
            if (line.startsWith("{item:")) {
                hologram.addDropLine(false, ItemBuilder.builder(line.split(":")[1].split("}")[0]).build());
            } else if (line.startsWith("{bigitem:")) {
                hologram.addBigItemLine(false, ItemBuilder.builder(line.split(":")[1].split("}")[0]).build());
            } else {
                hologram.addTextLine(line);
            }
        });
        hologram.setPublic(true);
    }
}

