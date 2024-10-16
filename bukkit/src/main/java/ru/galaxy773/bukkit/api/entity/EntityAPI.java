package ru.galaxy773.bukkit.api.entity;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import ru.galaxy773.bukkit.api.entity.npc.NPC;
import ru.galaxy773.bukkit.api.entity.npc.NpcType;
import ru.galaxy773.bukkit.api.entity.npc.types.HumanNPC;
import ru.galaxy773.bukkit.api.entity.stand.CustomStand;
import ru.galaxy773.multiplatform.impl.skin.Skin;

import java.util.List;

public interface EntityAPI {

    CustomStand createStand(JavaPlugin javaPlugin, Location location);

    HumanNPC createNPC(JavaPlugin javaPlugin, Location location, Skin skin);
    HumanNPC createNPC(JavaPlugin javaPlugin, Location location, String value, String signature);
    HumanNPC createNPC(JavaPlugin javaPlugin, Location location, Player player);

    <T extends NPC> T createNPC(JavaPlugin javaPlugin, Location location, NpcType type);

    List<NPC> getNPCs();

    <T extends NPC> List<T> getNPC(NpcType type);

    List<CustomStand> getCustomStands();
}
