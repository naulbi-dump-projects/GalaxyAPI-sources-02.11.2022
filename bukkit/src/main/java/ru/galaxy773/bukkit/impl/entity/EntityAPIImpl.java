package ru.galaxy773.bukkit.impl.entity;

import lombok.Getter;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import ru.galaxy773.bukkit.api.entity.EntityAPI;
import ru.galaxy773.bukkit.api.entity.npc.NPC;
import ru.galaxy773.bukkit.api.entity.npc.NpcType;
import ru.galaxy773.bukkit.api.entity.npc.types.HumanNPC;
import ru.galaxy773.bukkit.api.entity.stand.CustomStand;
import ru.galaxy773.bukkit.impl.entity.customstand.CraftStand;
import ru.galaxy773.bukkit.impl.entity.customstand.StandListener;
import ru.galaxy773.bukkit.impl.entity.customstand.StandManager;
import ru.galaxy773.bukkit.impl.entity.npc.NPCListener;
import ru.galaxy773.bukkit.impl.entity.npc.NPCManager;
import ru.galaxy773.bukkit.impl.entity.npc.type.*;
import ru.galaxy773.bukkit.impl.entity.tracker.EntityTrackerListener;
import ru.galaxy773.bukkit.impl.entity.tracker.TrackerEntity;
import ru.galaxy773.bukkit.impl.entity.tracker.TrackerManager;
import ru.galaxy773.bukkit.nms.api.NmsAPI;
import ru.galaxy773.bukkit.nms.interfaces.NmsManager;
import ru.galaxy773.multiplatform.impl.skin.Skin;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@SuppressWarnings("unchecked")
@Getter
public class EntityAPIImpl implements EntityAPI, TrackerManager {

    private final StandManager standManager;
    private final NPCManager npcManager;
    private final NmsManager nmsManager = NmsAPI.getManager();
    
    public EntityAPIImpl() {
        this.standManager = new StandManager();
        this.npcManager = new NPCManager();
        new StandListener(this);
        new NPCListener(this);
        new EntityTrackerListener(this);
        new EntityAPIListener(standManager, npcManager);
    }

    @Override
    public CustomStand createStand(JavaPlugin javaPlugin, Location location) {
        return new CraftStand(javaPlugin, standManager, location);
    }

    @Override
    public HumanNPC createNPC(JavaPlugin javaPlugin, Location location, String value, String signature) {
        return new CraftHumanNPC(javaPlugin, npcManager, location, value, signature);
    }

    @Override
    public HumanNPC createNPC(JavaPlugin javaPlugin, Location location, Player player) {
        if (player != null && player.isOnline()) {
            return createNPC(javaPlugin, location, Skin.DEFAULT_SKIN);
        }

        return createNPC(javaPlugin, location, "", "");
    }

    @Override
    public <T extends NPC> T createNPC(JavaPlugin javaPlugin, Location location, NpcType type) {
        NPC npc;
        switch (type) {
            case COW:
                npc = new CraftCowNPC(javaPlugin, npcManager, location);
                break;
            case ZOMBIE:
                npc = new CraftZombieNPC(javaPlugin, npcManager, location);
                break;
            case VILLAGER:
                npc = new CraftVillagerNPC(javaPlugin, npcManager, location);
                break;
            case MUSHROOM_COW:
                npc = new CraftMushroomCowNPC(javaPlugin, npcManager, location);
                break;
            case SLIME:
                npc = new CraftSlimeNPC(javaPlugin, npcManager, location);
                break;
            case CREEPER:
                npc = new CraftCreeperNPC(javaPlugin, npcManager, location);
                break;
            case WOLF:
                npc = new CraftWolfNPC(javaPlugin, npcManager, location);
                break;
            case BLAZE:
                npc = new CraftBlazeNPC(javaPlugin, npcManager, location);
                break;
            case ENDER_DRAGON:
                npc = new CraftEnderDragonNPC(javaPlugin, npcManager, location);
                break;
            case HUMAN:
            default: 
                npc = createNPC(javaPlugin, location, "", "");
        }

        if (npc.getType() != type) {
            npcManager.getNPCs().remove(npc.getEntityID());
            throw new IllegalArgumentException("NPC type not found");
        }
        return (T) npc;
    }

    @Override
    public List<NPC> getNPCs() {
        return npcManager.getNPCs().values()
                .stream()
                .map(craftNPC -> (NPC)craftNPC)
                .collect(Collectors.toList());
    }

    @Override
    public <T extends NPC> List<T> getNPC(NpcType type) {
        return npcManager.getNPCs().values()
                .stream()
                .filter(craftNPC -> craftNPC.getType() == type)
                .map(craftNPC -> (T)craftNPC)
                .collect(Collectors.toList());
    }

    @Override
    public List<CustomStand> getCustomStands() {
        return standManager.getStands().values()
                .stream()
                .map(craftStand -> (CustomStand)craftStand)
                .collect(Collectors.toList());
    }

    @Override
    public List<TrackerEntity> getTrackerEntities() {
        List<TrackerEntity> entities = new ArrayList<>();
        entities.addAll(npcManager.getNPCs().values());
        entities.addAll(standManager.getStands().values());
        return entities;
    }

	@Override
	public HumanNPC createNPC(JavaPlugin javaPlugin, Location location, Skin skin) {
		return createNPC(javaPlugin, location, skin.getValue(), skin.getSignature());
	}
}
