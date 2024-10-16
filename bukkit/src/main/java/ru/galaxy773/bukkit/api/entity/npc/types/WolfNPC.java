package ru.galaxy773.bukkit.api.entity.npc.types;

import org.bukkit.DyeColor;
import ru.galaxy773.bukkit.api.entity.npc.NPC;

public interface WolfNPC extends NPC {

    DyeColor getCollarColor();

    void setCollarColor(DyeColor color);

    boolean isAngry();

    void setAngry(boolean angry);

    boolean isSitting();

    void setSitting(boolean sitting);
}
