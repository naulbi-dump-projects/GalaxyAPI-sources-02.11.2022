package ru.galaxy773.bukkit.nms.interfaces.packet;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import ru.galaxy773.bukkit.api.effect.ParticleEffect;
import ru.galaxy773.bukkit.api.entity.EquipType;
import ru.galaxy773.bukkit.api.entity.npc.AnimationNpcType;
import ru.galaxy773.bukkit.api.scoreboard.DisplaySlot;
import ru.galaxy773.bukkit.nms.interfaces.GWorldBorder;
import ru.galaxy773.bukkit.nms.interfaces.entity.GEntity;
import ru.galaxy773.bukkit.nms.interfaces.entity.GEntityLiving;
import ru.galaxy773.bukkit.nms.interfaces.entity.GEntityPlayer;
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
import ru.galaxy773.bukkit.nms.types.*;

public interface PacketContainer {

    void sendPacket(Player player, GPacket... dPackets);

    void sendChatPacket(Player player, String message, ChatMessageType messageType);

    void sendTitlePacket(Player player, TitleActionType type, String message);
    
    void sendTitlePacket(Player player, int fadeIn, int stay, int fadeOut);
    
    void sendWorldBorderPacket(Player player, GWorldBorder border, WorldBorderActionType type);
    
    PacketScoreBoardTeam getScoreBoardTeamPacket(GTeam team, TeamAction action);

    PacketDisplayObjective getDisplayObjectivePacket(DisplaySlot slot, GObjective objective);

    PacketScoreboardObjective getScoreboardObjectivePacket(GObjective objective, ObjectiveActionMode mode);

    PacketScoreboardScore getScoreboardScorePacket(GScore score, ScoreboardAction action);

    PacketNamedEntitySpawn getNamedEntitySpawnPacket(GEntityPlayer entityPlayer);

    PacketPlayerInfo getPlayerInfoPacket(GEntityPlayer entityPlayer, PlayerInfoActionType actionType);

    PacketAnimation getAnimationPacket(GEntity entity, AnimationNpcType animation);

    PacketAttachEntity getAttachEntityPacket(GEntity entity, GEntity vehicle);

    PacketEntityDestroy getEntityDestroyPacket(int... entityIDs);

    PacketMount getMountPacket(GEntity entity);

    PacketEntityMetadata getEntityMetadataPacket(GEntity entity);

    PacketCamera getCameraPacket(Player player);

    PacketEntityLook getEntityLookPacket(GEntity entity, byte yaw, byte pitch);

    PacketEntityEquipment getEntityEquipmentPacket(GEntity entity, EquipType slot, ItemStack itemStack);

    PacketEntityHeadRotation getEntityHeadRotationPacket(GEntity entity, byte yaw);

    PacketSpawnEntity getSpawnEntityPacket(GEntity entity, EntitySpawnType entitySpawnType, int objectData);

    PacketSpawnEntityLiving getSpawnEntityLivingPacket(GEntityLiving entityLiving);

    PacketEntityTeleport getEntityTeleportPacket(GEntity entity);

    PacketBed getBedPacket(GEntityPlayer entity, Location bed);

    PacketWorldParticles getWorldParticlesPacket(ParticleEffect effect, boolean longDistance, Location center,
                                                 float offsetX, float offsetY, float offsetZ, float speed,
                                                 int amount, int... data);
}
