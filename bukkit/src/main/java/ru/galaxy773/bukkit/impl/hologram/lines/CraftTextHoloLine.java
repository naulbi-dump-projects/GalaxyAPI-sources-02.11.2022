package ru.galaxy773.bukkit.impl.hologram.lines;

import lombok.Getter;
import org.bukkit.Location;
import ru.galaxy773.bukkit.api.hologram.lines.TextHoloLine;
import ru.galaxy773.bukkit.impl.hologram.CraftHologram;

@Getter
public class CraftTextHoloLine extends CraftHoloLine implements TextHoloLine {

    private String text;
    
    public CraftTextHoloLine(CraftHologram hologram, Location location, String text) {
        super(hologram, location);
        this.text = text;
        customStand.setCustomName(text);
    }

    @Override
    public void setText(String text) {
        this.text = text;
        customStand.setCustomName(text);
    }
}
