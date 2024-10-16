package ru.galaxy773.bukkit.api.tops.armorstand;

import lombok.Getter;
import org.bukkit.entity.Player;
import ru.galaxy773.bukkit.api.hologram.Hologram;

import java.util.List;

@Getter
final class StandPlayer {

    private final TopManager manager;
    private final Player gamer;
    private Top topType;
    private boolean newPlayer;

    StandPlayer(TopManager manager, Player gamer) {
        this.manager = manager;
        this.gamer = gamer;
        Top selectedType = manager.getStandTopSelection().getSelectedType(gamer);
        if (selectedType != null) {
            this.topType = selectedType;
            this.newPlayer = false;
        } else {
            this.topType = manager.getFirstTop();
            this.newPlayer = true;
        }
        show();
    }
    
    void changeSelected() {
        List<StandTop> stand = manager.getAllStands(topType);
        if (stand != null) {
            for (StandTop standTop : stand) {
                standTop.removeTo(gamer, true);
            }
        }
        Hologram hologramMiddle = topType.getHologramMiddle();
        if (hologramMiddle != null) {
            hologramMiddle.removeTo(gamer);
        }
        Top newTopType = manager.getFirstTop();

        int newId = topType.getId() + 1;
        if (manager.getTops().size() > newId) {
            newTopType = manager.getTop(newId);
        }
        this.topType = newTopType;
        show();
        manager.getStandTopSelection().changeSelectedType(this);
        this.newPlayer = false;
    }

    private void show() {
        List<StandTop> stand = manager.getAllStands(topType);
        if (stand != null) {
            for (StandTop standTop : stand) {
                standTop.showTo(gamer, true);
            }
        }
        Hologram hologramMiddle = topType.getHologramMiddle();
        if (hologramMiddle != null) {
            hologramMiddle.showTo(gamer);
        }
    }
}
