package ru.galaxy773.bukkit.api.tops.armorstand;

import lombok.Getter;
import org.bukkit.Location;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Getter
public final class TopManager {

    private final JavaPlugin javaPlugin;
    private final StandTopSelection standTopSelection;
    private final StandTopListener standTopListener;
    private final StandPlayerStorage standPlayerStorage;
    private final List<Top> tops = new ArrayList<Top>();
    private final Map<Top, List<StandTop>> allTops = new ConcurrentHashMap<>();
    
    public TopManager(JavaPlugin javaPlugin) {
        this.javaPlugin = javaPlugin;
        this.standTopSelection = new StandTopSelection(this);
        this.standPlayerStorage = new StandPlayerStorage();
        this.standTopListener = new StandTopListener(this);
    }

    public List<Top> getTops() {
        return Collections.unmodifiableList(tops);
    }

    public Map<Top, List<StandTop>> getAllTops() {
        return Collections.unmodifiableMap(allTops);
    }
    public Top getTop(int type) {
        if (tops.size() <= type) {
            return null;
        }
        return tops.get(type);
    }
    public Top getFirstTop() {
        if (tops.isEmpty()) {
            return null;
        }
        return getTop(0);
    }

    public int size() {
        return allTops.size();
    }

    public void createTop(Top topType, List<Location> locations) {
        if (tops.contains(topType)) {
            return;
        }
        List<StandTop> standTops = new ArrayList<>();
        int pos = 1;
        for (Location location : locations) {
            standTops.add(new StandTop(javaPlugin, topType, location, pos++));
        }
        tops.add(topType);
        allTops.put(topType, standTops);
    }

    public void updateStandData(List<StandTopData> standTopData) {
        for (StandTopData data : standTopData) {
            if (!tops.contains(data.getTop())) {
                continue;
            }

            StandTop standTop = getStandByPosition(data.getTop(), data.getPosition());
            if (standTop != null) {
                standTop.setStandTopData(data);
            }
        }
    }
    public StandTop getStandByPosition(Top top, int position) {
        List<StandTop> allStands = getAllStands(top);
        if (allStands.size() < position) {
            return null;
        }
        return allStands.get(position - 1);
    }

    public List<StandTop> getAllStands(Top top) {
        List<StandTop> standTops = allTops.get(top);
        if (standTops != null) {
            return Collections.unmodifiableList(standTops);
        }
        return Collections.emptyList();
    }
    
    public List<StandTop> getAllStands() {
        List<StandTop> standTops = new ArrayList<>();
        allTops.values().forEach(standTops::addAll);
        return standTops;
    }
}
