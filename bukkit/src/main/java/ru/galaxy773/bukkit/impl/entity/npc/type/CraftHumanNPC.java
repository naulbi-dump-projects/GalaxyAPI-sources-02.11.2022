package ru.galaxy773.bukkit.impl.entity.npc.type;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import ru.galaxy773.bukkit.api.entity.npc.AnimationNpcType;
import ru.galaxy773.bukkit.api.entity.npc.NpcType;
import ru.galaxy773.bukkit.api.entity.npc.types.HumanNPC;
import ru.galaxy773.bukkit.api.utils.bukkit.BukkitUtil;
import ru.galaxy773.bukkit.impl.entity.npc.CraftNPC;
import ru.galaxy773.bukkit.impl.entity.npc.NPCManager;
import ru.galaxy773.bukkit.nms.interfaces.entity.GEntityPlayer;
import ru.galaxy773.bukkit.nms.interfaces.packet.entity.PacketEntityTeleport;
import ru.galaxy773.bukkit.nms.interfaces.packet.entityplayer.PacketBed;
import ru.galaxy773.bukkit.nms.interfaces.packet.entityplayer.PacketNamedEntitySpawn;
import ru.galaxy773.bukkit.nms.interfaces.packet.entityplayer.PacketPlayerInfo;
import ru.galaxy773.bukkit.nms.interfaces.packet.scoreboard.PacketScoreBoardTeam;
import ru.galaxy773.bukkit.nms.scoreboard.GTeam;
import ru.galaxy773.bukkit.nms.scoreboard.TagVisibility;
import ru.galaxy773.bukkit.nms.scoreboard.TeamAction;
import ru.galaxy773.bukkit.nms.types.PlayerInfoActionType;
import ru.galaxy773.multiplatform.api.skin.SkinType;
import ru.galaxy773.multiplatform.impl.skin.Skin;

import java.util.UUID;

@SuppressWarnings("deprecation")
public class CraftHumanNPC extends CraftNPC implements HumanNPC {

    private boolean bed = false;
    private Skin skin;
    private ChatColor color;
    
    public CraftHumanNPC(JavaPlugin javaPlugin, NPCManager npcManager, Location location, String value, String signature) {
        super(javaPlugin, npcManager, location);
        this.color = ChatColor.GOLD;
        createEntity(value, signature);
        npcManager.addNPC(this);
    }

    private void createEntity(String value, String signature) {
        GameProfile gameProfile = new GameProfile(UUID.randomUUID(), name);
        gameProfile.getProperties().put("textures", new Property("textures", value, signature));
        this.skin = new Skin(this.name, value, signature, SkinType.CUSTOM);
        this.entity = NMS_MANAGER.createEntityPlayer(location.getWorld(), gameProfile);
        entity.setLocation(location);
        entity.setCustomNameVisible(false);
        entity.setCustomName(name);
        entity.watch(13, (byte)127);
    }
    
    @Override
    public void changeSkin(String value, String signature) {
        for (String name : getVisiblePlayers()) {
            destroy(Bukkit.getPlayerExact(name));
        }
        createEntity(value, signature);
        npcManager.addNPC(this);
        BukkitUtil.runTaskLaterAsync(5L, ()-> {
            for (String name : getVisiblePlayers()) {
                Player player = Bukkit.getPlayer(name);
                if (player == null || !player.isOnline()) {
                    continue;
                }
                spawn(player);
            }
        });
    }

    @Override
    public void changeSkin(Skin skin) {
        changeSkin(skin.getValue(), skin.getSignature());
    }

    @Override
    public void setBed(boolean bed) {
        if (entity == null) {
            return;
        }
        if (this.bed == bed) {
            return;
        }
        this.bed = bed;
        if (bed) {
            Location bedLocation = this.getLocation();
            bedLocation.setY(1);
            PacketBed packetBed = PACKET_CONTAINER.getBedPacket(getEntity(), bedLocation);
            for (String name : getVisiblePlayers()) {
                Player player = Bukkit.getPlayer(name);
                if (player == null) {
                    continue;
                }

                player.sendBlockChange(bedLocation, Material.BED_BLOCK, (byte)0);
                packetBed.sendPacket(player);
            }
            return;
        }
        animation(AnimationNpcType.LEAVE_BED);
        PacketEntityTeleport teleportPacket = PACKET_CONTAINER.getEntityTeleportPacket(entity);
        sendNearby(teleportPacket);
    }

    @Override
    public boolean isLeavedBed() {
        return !bed;
    }

    @Override
    public Skin getSkin() {
        if (skin == null) {
            return Skin.DEFAULT_SKIN;
        }
        return skin;
    }

    public GEntityPlayer getEntity() {
        return (GEntityPlayer) entity;
    }

    @Override
    public GEntityPlayer createNMSEntity() {
        return null;
    }

    @Override
    public void spawnEntity(Player player) {
        if (entity == null) {
            return;
        }
        npcManager.addPlayerToTeam(player, this, color);
        PacketPlayerInfo addPacket = PACKET_CONTAINER.getPlayerInfoPacket((GEntityPlayer) entity,
                PlayerInfoActionType.ADD_PLAYER);
        addPacket.sendPacket(player);
        PacketNamedEntitySpawn spawnPacket = PACKET_CONTAINER.getNamedEntitySpawnPacket((GEntityPlayer) entity);
        spawnPacket.sendPacket(player);
        if (bed) {
            Location bedLocation = this.getLocation(); 
            bedLocation.setY(1);
            PacketBed packetBed = PACKET_CONTAINER.getBedPacket(getEntity(), bedLocation);
            player.sendBlockChange(bedLocation, Material.BED_BLOCK, (byte)0);
            packetBed.sendPacket(player);
        }
        BukkitUtil.runTaskLaterAsync(100L, ()-> {
            PacketPlayerInfo removePacket = PACKET_CONTAINER.getPlayerInfoPacket(getEntity(),
                    PlayerInfoActionType.REMOVE_PLAYER);
            removePacket.sendPacket(player);
        });
    }


    @Override
    public void setGlowing(ChatColor color) {
        if (this.color == color) {
            return;
        }
        this.color = color;
        GTeam team = new GTeam(this.name + "_TEAM");
        team.setPrefix(color.toString());
        team.setVisibility(TagVisibility.NEVER);
        PacketScoreBoardTeam packet = PACKET_CONTAINER.getScoreBoardTeamPacket(team, TeamAction.UPDATE);
        sendNearby(packet);
        setGlowing(true);
    }

    @Override
    public NpcType getType() {
        return NpcType.HUMAN;
    }
}
