package ru.galaxy773.bukkit.api.tops.armorstand;

import org.bukkit.entity.Player;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public final class StandTopSelection {

    private final TopManager standTopManager;
    private final Map<String, Integer> selectedTypes;
    
    StandTopSelection(TopManager standTopManager) {
        this.standTopManager = standTopManager;
        this.selectedTypes = new ConcurrentHashMap<String, Integer>();
    }
    public Top getSelectedType(Player gamer) {
        if (gamer == null) {
            return null;
        }
        return selectedTypes.containsKey(gamer.getName()) ? standTopManager.getTop(selectedTypes.get(gamer.getName())) : null;
    }
    
    void changeSelectedType(StandPlayer standPlayer) {
        Player gamer = standPlayer.getGamer();
        if (gamer == null) {
            return;
        }
        int topTypeID = standPlayer.getTopType().getId();
        if(selectedTypes.containsKey(gamer.getName())) {
        	selectedTypes.replace(gamer.getName(), topTypeID);
        	return;
        }
        selectedTypes.put(gamer.getName(), topTypeID);
    }
    
    public void removePlayer(String playerName) {
    	selectedTypes.remove(playerName);
    }
    
    void onDisable() {
        this.selectedTypes.clear();
    }
}
