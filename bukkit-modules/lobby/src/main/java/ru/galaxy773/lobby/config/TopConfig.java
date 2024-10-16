package ru.galaxy773.lobby.config;

import java.util.LinkedList;
import java.util.List;

import lombok.Getter;
import org.bukkit.Location;
import ru.galaxy773.bukkit.api.utils.bukkit.LocationUtil;
import ru.galaxy773.lobby.top.LobbyTop;

@Getter
public class TopConfig extends LobbyConfig {

    private final List<Location> standLocations = new LinkedList<>();
    private Location middleLocation;

    public TopConfig() {
        super("tops.yml");
    }

    @Override
    public void load() {
        this.middleLocation = LocationUtil.stringToLocation(this.config.getString("hologram_location"), false);
        this.config.getStringList("stands_locations").forEach(location -> this.standLocations.add(LocationUtil.stringToLocation(location, (boolean)true)));
    }

    @Override
    public void init() {
        new LobbyTop(JAVA_PLUGIN, this);
    }
}

