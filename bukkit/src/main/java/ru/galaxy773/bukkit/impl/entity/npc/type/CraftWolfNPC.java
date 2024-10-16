package ru.galaxy773.bukkit.impl.entity.npc.type;

import org.bukkit.DyeColor;
import org.bukkit.Location;
import org.bukkit.plugin.java.JavaPlugin;
import ru.galaxy773.bukkit.api.entity.npc.NpcType;
import ru.galaxy773.bukkit.api.entity.npc.types.WolfNPC;
import ru.galaxy773.bukkit.impl.entity.npc.NPCManager;
import ru.galaxy773.bukkit.nms.interfaces.entity.GEntityLiving;
import ru.galaxy773.bukkit.nms.interfaces.entity.GEntityWolf;

public class CraftWolfNPC extends CraftLivingNPC implements WolfNPC {

    public CraftWolfNPC(JavaPlugin javaPlugin, NPCManager npcManager, Location location) {
        super(javaPlugin, npcManager, location);
    }

    @Override
    public GEntityLiving createNMSEntity() {
        GEntityWolf wolf = NMS_MANAGER.createGEntity(GEntityWolf.class, location);
        wolf.setAngry(false);
        wolf.setSitting(false);
        wolf.setCollarColor(DyeColor.RED);
        return wolf;
    }

    @Override
    public DyeColor getCollarColor() {
        return ((GEntityWolf)entity).getCollarColor();
    }

    @Override
    public void setCollarColor(DyeColor color) {
        ((GEntityWolf)entity).setCollarColor(color);
        sendPacketMetaData();
    }

    @Override
    public boolean isAngry() {
        return ((GEntityWolf)entity).isAngry();
    }

    @Override
    public void setAngry(boolean angry) {
        ((GEntityWolf)entity).setAngry(angry);
        sendPacketMetaData();
    }

    @Override
    public boolean isSitting() {
        return ((GEntityWolf)entity).isSitting();
    }

    @Override
    public void setSitting(boolean sitting) {
        ((GEntityWolf)entity).setSitting(sitting);
        sendPacketMetaData();
    }

    @Override
    public NpcType getType() {
        return NpcType.WOLF;
    }
}
