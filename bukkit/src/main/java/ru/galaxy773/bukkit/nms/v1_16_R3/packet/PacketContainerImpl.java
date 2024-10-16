package ru.galaxy773.bukkit.nms.v1_16_R3.packet;

import ru.galaxy773.bukkit.nms.interfaces.GWorldBorder;
import ru.galaxy773.bukkit.nms.interfaces.packet.GPacket;
import ru.galaxy773.bukkit.nms.v1_16_R3.GWorldBorderImpl;
import net.minecraft.server.v1_16_R3.*;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_16_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import ru.galaxy773.bukkit.api.effect.ParticleEffect;
import ru.galaxy773.bukkit.api.entity.EquipType;
import ru.galaxy773.bukkit.api.entity.npc.AnimationNpcType;
import ru.galaxy773.bukkit.api.scoreboard.DisplaySlot;
import ru.galaxy773.bukkit.nms.interfaces.entity.GEntity;
import ru.galaxy773.bukkit.nms.interfaces.entity.GEntityLiving;
import ru.galaxy773.bukkit.nms.interfaces.entity.GEntityPlayer;
import ru.galaxy773.bukkit.nms.interfaces.packet.PacketContainer;
import ru.galaxy773.bukkit.nms.interfaces.packet.entity.*;
import ru.galaxy773.bukkit.nms.interfaces.packet.entityplayer.PacketBed;
import ru.galaxy773.bukkit.nms.interfaces.packet.entityplayer.PacketCamera;
import ru.galaxy773.bukkit.nms.interfaces.packet.entityplayer.PacketNamedEntitySpawn;
import ru.galaxy773.bukkit.nms.interfaces.packet.entityplayer.PacketPlayerInfo;
import ru.galaxy773.bukkit.nms.interfaces.packet.scoreboard.PacketDisplayObjective;
import ru.galaxy773.bukkit.nms.interfaces.packet.scoreboard.PacketScoreBoardTeam;
import ru.galaxy773.bukkit.nms.interfaces.packet.scoreboard.PacketScoreboardObjective;
import ru.galaxy773.bukkit.nms.interfaces.packet.scoreboard.PacketScoreboardScore;
import ru.galaxy773.bukkit.nms.interfaces.packet.world.PacketWorldParticles;
import ru.galaxy773.bukkit.nms.scoreboard.*;
import ru.galaxy773.bukkit.nms.types.EntitySpawnType;
import ru.galaxy773.bukkit.nms.types.PlayerInfoActionType;
import ru.galaxy773.bukkit.nms.types.TitleActionType;
import ru.galaxy773.bukkit.nms.types.WorldBorderActionType;
import ru.galaxy773.bukkit.nms.v1_16_R3.packet.entity.*;
import ru.galaxy773.bukkit.nms.v1_16_R3.packet.entityplayer.PacketCameraImpl;
import ru.galaxy773.bukkit.nms.v1_16_R3.packet.entityplayer.PacketNamedEntitySpawnImpl;
import ru.galaxy773.bukkit.nms.v1_16_R3.packet.entityplayer.PacketPlayerInfoImpl;
import ru.galaxy773.bukkit.nms.v1_16_R3.packet.scoreboard.PacketDisplayObjectiveImpl;
import ru.galaxy773.bukkit.nms.v1_16_R3.packet.scoreboard.PacketScoreBoardTeamImpl;
import ru.galaxy773.bukkit.nms.v1_16_R3.packet.scoreboard.PacketScoreboardObjectiveImpl;
import ru.galaxy773.bukkit.nms.v1_16_R3.packet.scoreboard.PacketScoreboardScoreImpl;

import java.util.Arrays;
import java.util.UUID;

public class PacketContainerImpl implements PacketContainer {

    @Override
    public void sendPacket(Player player, GPacket... tPackets) {
        if (tPackets.length == 0) {
            return;
        }

        Arrays.asList(tPackets).forEach(packet -> packet.sendPacket(player));
    }

    @Override
    public void sendChatPacket(Player player, String message,
                               ru.galaxy773.bukkit.nms.types.ChatMessageType messageType) {
        PacketPlayOutChat packetPlayOutChat = new PacketPlayOutChat(IChatBaseComponent.ChatSerializer.a(
                (messageType == ru.galaxy773.bukkit.nms.types.ChatMessageType.GAME_INFO) ? ("{\"text\": \"" + message + "\"}") : message),
                ChatMessageType.valueOf(messageType.name()),
                UUID.randomUUID());
        sendPacket(player, packetPlayOutChat);
    }
    
    @Override
    public void sendTitlePacket(Player player, final TitleActionType type, final String message) {
        final PacketPlayOutTitle packet = new PacketPlayOutTitle(PacketPlayOutTitle.EnumTitleAction.valueOf(type.name()), (IChatBaseComponent)IChatBaseComponent.ChatSerializer.a("{\"text\": \"" + message + "\"}"));
        this.sendPacket(player, packet);
    }
    
    @Override
    public void sendTitlePacket(Player player, int fadeIn, int stay, int fadeOut) {
        final PacketPlayOutTitle packet = new PacketPlayOutTitle(fadeIn, stay, fadeOut);
        this.sendPacket(player, packet);
    }

    @Override
    public void sendWorldBorderPacket(Player player, GWorldBorder border, WorldBorderActionType type) {
        WorldBorder worldBorder = ((GWorldBorderImpl)border).get();
        PacketPlayOutWorldBorder packet = new PacketPlayOutWorldBorder(worldBorder,
                PacketPlayOutWorldBorder.EnumWorldBorderAction.valueOf(type.name()));
        sendPacket(player, packet);
    }

    @Override
    public PacketScoreBoardTeam getScoreBoardTeamPacket(GTeam team, TeamAction action) {
        return new PacketScoreBoardTeamImpl(team, action);
    }
    
    @Override
    public PacketDisplayObjective getDisplayObjectivePacket(DisplaySlot slot, GObjective objective) {
        return new PacketDisplayObjectiveImpl(slot, objective);
    }
    
    @Override
    public PacketScoreboardObjective getScoreboardObjectivePacket(GObjective objective, ObjectiveActionMode mode) {
        return new PacketScoreboardObjectiveImpl(objective, mode);
    }
    
    @Override
    public PacketScoreboardScore getScoreboardScorePacket(GScore score, ScoreboardAction action) {
        return new PacketScoreboardScoreImpl(score, action);
    }
    
    @Override
    public PacketNamedEntitySpawn getNamedEntitySpawnPacket(GEntityPlayer entityPlayer) {
        return new PacketNamedEntitySpawnImpl(entityPlayer);
    }
    
    @Override
    public PacketPlayerInfo getPlayerInfoPacket(GEntityPlayer entityPlayer, PlayerInfoActionType actionType) {
        return new PacketPlayerInfoImpl(entityPlayer, actionType);
    }
    
    private void sendPacket(Player player, Packet<?> packet) {
        if (player == null || !player.isOnline()) {
            return;
        }

        EntityPlayer handle = ((CraftPlayer)player).getHandle();
        if (handle == null) {
            return;
        }

        PlayerConnection playerConnection = handle.playerConnection;
        if (playerConnection == null) {
            return;
        }

        playerConnection.sendPacket(packet);
    }
    
    @Override
    public PacketAnimation getAnimationPacket(GEntity entity, AnimationNpcType animation) {
        return new PacketAnimationImpl(entity, animation);
    }
    
    @Override
    public PacketAttachEntity getAttachEntityPacket(GEntity entity, GEntity vehicle) {
        return new PacketAttachEntityImpl(entity, vehicle);
    }
    
    @Override
    public PacketEntityDestroy getEntityDestroyPacket(int... entityIDs) {
        return new PacketEntityDestroyImpl(entityIDs);
    }
    
    @Override
    public PacketMount getMountPacket(GEntity entity) {
        return new PacketMountImpl(entity);
    }
    
    @Override
    public PacketEntityMetadata getEntityMetadataPacket(GEntity entity) {
        return new PacketEntityMetadataImpl(entity);
    }
    
    @Override
    public PacketCamera getCameraPacket(Player player) {
        return new PacketCameraImpl(player);
    }
    
    @Override
    public PacketEntityLook getEntityLookPacket(GEntity entity, byte yaw, byte pitch) {
        return new PacketEntityLookImpl(entity, yaw, pitch);
    }
    
    @Override
    public PacketEntityEquipment getEntityEquipmentPacket(GEntity entity, EquipType slot, ItemStack itemStack) {
        return new PacketEntityEquipmentImpl(entity, slot, itemStack);
    }
    
    @Override
    public PacketEntityHeadRotation getEntityHeadRotationPacket(GEntity entity, byte yaw) {
        return new PacketEntityHeadRotationImpl(entity, yaw);
    }
    
    @Override
    public PacketSpawnEntity getSpawnEntityPacket(GEntity entity, EntitySpawnType entitySpawnType, int objectData) {
        return new PacketSpawnEntityImpl(entity, entitySpawnType, objectData);
    }
    
    @Override
    public PacketSpawnEntityLiving getSpawnEntityLivingPacket(GEntityLiving entityLiving) {
        return new PacketSpawnEntityLivingImpl(entityLiving);
    }
    
    @Override
    public PacketEntityTeleport getEntityTeleportPacket(GEntity entity) {
        return new PacketEntityTeleportImpl(entity);
    }
    
    @Override
    public PacketBed getBedPacket(GEntityPlayer entity, Location bed) {
        return null;
    }

    @Override
    public PacketWorldParticles getWorldParticlesPacket(ParticleEffect effect, boolean longDistance, Location center,
                                                        float offsetX, float offsetY, float offsetZ, float speed,
                                                        int amount, int... data) {
        throw new UnsupportedOperationException();
    }
}
