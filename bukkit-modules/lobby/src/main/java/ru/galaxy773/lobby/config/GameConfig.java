package ru.galaxy773.lobby.config;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.plugin.java.JavaPlugin;
import ru.galaxy773.bukkit.api.BukkitAPI;
import ru.galaxy773.bukkit.api.core.CoreAPI;
import ru.galaxy773.bukkit.api.entity.EntityAPI;
import ru.galaxy773.bukkit.api.entity.npc.NPC;
import ru.galaxy773.bukkit.api.entity.npc.types.HumanNPC;
import ru.galaxy773.bukkit.api.hologram.Hologram;
import ru.galaxy773.bukkit.api.hologram.HologramAPI;
import ru.galaxy773.bukkit.api.utils.bukkit.ItemBuilder;
import ru.galaxy773.bukkit.api.utils.bukkit.LocationUtil;
import ru.galaxy773.lobby.listeners.LobbyListener;
import ru.galaxy773.multiplatform.api.placeholder.PlaceholderAPI;
import ru.galaxy773.multiplatform.impl.skin.Skin;

public class GameConfig extends LobbyConfig {

    private final PlaceholderAPI placeholderAPI = BukkitAPI.getPlaceholderAPI();
    private final EntityAPI entityAPI = BukkitAPI.getEntityAPI();
    private final HologramAPI hologramAPI = BukkitAPI.getHologramAPI();
    private final Map<HumanNPC, String> npc = new HashMap<>();
    private final Map<Hologram, String> holograms = new HashMap<>();
    private Set<Block> jumpPads;
    private Location spawn;
    private int voidSpawn;
    private boolean presents;

    public GameConfig() {
        super("lobby.yml");
    }

    @Override
    public void load() {
        ConfigurationSection npcSection = this.config.getConfigurationSection("npc");
        npcSection.getValues(false).keySet().forEach(path -> {
            HumanNPC npc = this.entityAPI.createNPC(JAVA_PLUGIN, LocationUtil.stringToLocation(npcSection.getString(path + ".location"), true), Skin.createSkin(npcSection.getString(path + ".value"), npcSection.getString(path + ".signature")));
            if (npcSection.getString(path + ".item_in_hand") != null) {
                npc.getEntityEquip().setItemInMainHand(ItemBuilder.builder(npcSection.getString(path + ".item_in_hand")).build());
            }
            if (npcSection.getString(path + ".glowing_color") != null) {
                npc.setGlowing(ChatColor.valueOf(npcSection.getString(path + ".glowing_color").toUpperCase()));
                npc.setGlowing(true);
            }
            npc.setHeadLook(npcSection.getBoolean(path + "head_look"));
            npc.setPublic(true);
            if (npcSection.contains(path + ".command"))
               this.npc.put(npc, npcSection.getString(path + ".command"));
        });
        ConfigurationSection hologramsSection = this.config.getConfigurationSection("holograms");
        hologramsSection.getValues(false).keySet().forEach(path -> {
            Hologram hologram = this.hologramAPI.createHologram((JavaPlugin)JAVA_PLUGIN, LocationUtil.stringToLocation(hologramsSection.getString(path + ".location"), (boolean)false));
            hologramsSection.getStringList(path + ".lines").forEach(line -> {
                if (line.startsWith("item:")) {
                    hologram.addDropLine(false, ItemBuilder.builder(line.split(":")[1]).build());
                } else if (line.startsWith("bigitem:")) {
                    hologram.addBigItemLine(false, ItemBuilder.builder(line.split(":")[1]).build());
                } else if (line.contains("online:")) {
                    hologram.addAnimationLine(60L, () -> placeholderAPI.applyArgumented(line));
                } else {
                    hologram.addTextLine(line);
                }
            });
            hologram.setPublic(true);
            if (hologramsSection.contains(path + ".command"))
                this.holograms.put(hologram, hologramsSection.getString(path + ".command"));
        });
        this.jumpPads = this.config.getStringList("jump_pads").stream().map(location -> LocationUtil.stringToLocation(location, (boolean)false).getBlock()).collect(Collectors.toSet());
        this.spawn = LocationUtil.stringToLocation(this.config.getString("spawn"), true);
        this.voidSpawn = this.config.getInt("void_spawn");
        this.presents = this.config.getBoolean("presents");
    }

    @Override
    public void init() {
        new LobbyListener(JAVA_PLUGIN, this);
    }

    public String getNPCCommand(NPC npc) {
        return this.npc.get(npc);
    }

    public String getHologramCommand(Hologram hologram) {
        return this.holograms.get((Object)hologram);
    }

    public Set<Block> getJumpPads() {
        return this.jumpPads;
    }

    public Location getSpawn() {
        return this.spawn;
    }

    public int getVoidSpawn() {
        return this.voidSpawn;
    }

    public boolean isPresents() {
        return this.presents;
    }
}

