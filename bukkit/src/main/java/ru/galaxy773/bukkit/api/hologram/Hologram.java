package ru.galaxy773.bukkit.api.hologram;

import org.bukkit.Location;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;
import ru.galaxy773.bukkit.api.entity.depend.PacketObject;
import ru.galaxy773.bukkit.api.hologram.lines.AnimationHoloLine;
import ru.galaxy773.bukkit.api.hologram.lines.ItemDropLine;
import ru.galaxy773.bukkit.api.hologram.lines.ItemFloatingLine;
import ru.galaxy773.bukkit.api.hologram.lines.TextHoloLine;

import java.util.Collection;
import java.util.List;
import java.util.function.Supplier;

public interface Hologram extends PacketObject {
    
	JavaPlugin getPlugin();
    
    AnimationHoloLine addAnimationLine(long speed, Supplier<String> replacerLine);
    TextHoloLine addTextLine(String text);
    ItemDropLine addDropLine(boolean pickup, ItemStack item);
    ItemFloatingLine addBigItemLine(boolean rotate, ItemStack item);

    TextHoloLine insertTextLine(int index, String text);
    ItemDropLine insertDropLine(int index, boolean pickup, ItemStack item);
    ItemFloatingLine insertBigItemLine(int index, boolean rotate, ItemStack item);

    List<HoloLine> getHoloLines();
    <T extends HoloLine> T getHoloLine(int index);

    //ClickAction getInteractAction();
    
    void addTextLine(Collection<String> listText);
    
    //void setInteractAction(ClickAction clickAction);
   
    int getSize();
    
    void removeLine(int index);
    void removeLine(HoloLine line);

    Location getLocation();

    void onTeleport(Location location);
}
